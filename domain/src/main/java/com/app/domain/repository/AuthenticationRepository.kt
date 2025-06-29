package com.app.domain.repository

import com.app.domain.model.user.User
import com.app.domain.model.user.UserDTO
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

interface AuthenticationRepository {
    suspend fun validateIsUser(task: Task<GoogleSignInAccount>?, onSuccess: (isNotUser: Boolean) -> Unit)
    fun signInWithGoogle(task: Task<GoogleSignInAccount>?, onSuccess: (id: String, email: String, name: String) -> Unit)
    suspend fun saveUser(user: User)
    suspend fun deleteAccount(googleId: String, onSuccess: (Boolean) -> Unit)
    suspend fun selectUserFindById(googleId: String) : UserDTO
    suspend fun updateProfileUrl(googleId: String, profileUrl: String)
    suspend fun selectProfileUrl(googleId: String) : String
}