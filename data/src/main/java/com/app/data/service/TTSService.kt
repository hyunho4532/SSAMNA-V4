package com.app.data.service

import com.app.domain.model.tts.TTSRequest
import com.app.domain.model.tts.TTSResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TTSService {
    @POST("v1/text:synthesize")
    fun synthesizeSpeech(
        @Body request: TTSRequest,
        @Query("key") apiKey: String
    ): Call<TTSResponse>
}