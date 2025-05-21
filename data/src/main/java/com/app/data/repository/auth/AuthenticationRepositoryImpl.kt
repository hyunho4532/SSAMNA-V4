package com.app.data.repository.auth

import android.util.Log
import com.app.domain.model.user.User
import com.app.domain.model.user.UserDTO
import com.app.domain.repository.user.AuthenticationRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : AuthenticationRepository {
    override suspend fun validateIsUser(
        task: Task<GoogleSignInAccount>?,
        onSuccess: (isUser: Boolean) -> Unit
    ) {
        val isValidateUser = postgrest.from("User")
            .select {
                filter {
                    eq("google_id", task?.result?.id.toString())
                }
            }.decodeSingleOrNull<UserDTO>()

        onSuccess(
            isValidateUser?.email?.isEmpty() ?: true
        )
    }

    override fun signInWithGoogle(
        task: Task<GoogleSignInAccount>?,
        onSuccess: (id: String, email: String, name: String) -> Unit
    ) {
        val account = task?.getResult(ApiException::class.java)

        try {
            account?.let { signInAccount ->
                onSuccess(signInAccount.id.toString(), signInAccount.email.toString(), signInAccount.displayName.toString())
            } ?: run {
                onSuccess("", "", "")
            }
        } catch (e: ApiException) {
            onSuccess("", "", "")
        }
    }

    override suspend fun saveUser(user: User) {
        val userDTO = UserDTO(
            googleId = user.id,
            email = user.email,
            name = user.name,
            age = user.age.toInt(),
            recentExerciseCheck = user.recentExerciseCheck,
            recentExerciseName = user.recentExerciseName,
            recentWalkingCheck = user.recentWalkingCheck,
            recentWalkingOfWeek = user.recentWalkingOfWeek,
            recentWalkingOfTime = user.recentWalkingOfTime,
            targetPeriod = user.targetPeriod
        )

        postgrest.from("User").insert(userDTO)
    }

    override suspend fun selectUserFindById(googleId: String) : UserDTO {
        return withContext(Dispatchers.IO) {
            postgrest.from("User").select {
                filter {
                    eq("google_id", googleId)
                }
            }.decodeSingle<UserDTO>()
        }
    }

    override suspend fun updateProfileUrl(googleId: String, profileUrl: String) {
        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("p_user_id", JsonPrimitive(googleId))
                put("p_profile_url", JsonPrimitive(profileUrl))
            }

            postgrest.rpc("update_profile_url", params)
        }
    }

    override suspend fun selectProfileUrl(googleId: String): String {
        var profileUrl = ""

        withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("p_user_id", JsonPrimitive(googleId))
            }

            val result = postgrest.rpc("get_profile_url", params)

            Log.d("AuthenticationRepository", result.data)

            profileUrl = result.data
        }

        return profileUrl
    }
}