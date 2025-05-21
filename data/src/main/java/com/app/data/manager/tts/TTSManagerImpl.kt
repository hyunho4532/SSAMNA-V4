package com.app.data.manager.tts

import android.content.Context
import android.media.MediaPlayer
import android.util.Base64
import com.app.data.BuildConfig
import com.app.data.service.TTSService
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.tts.AudioConfig
import com.app.domain.model.tts.Input
import com.app.domain.model.tts.TTSRequest
import com.app.domain.model.tts.TTSResponse
import com.app.domain.model.tts.Voice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class TTSManagerImpl @Inject constructor(
    val context: Context
) : TTSManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://texttospeech.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun speak(text: String, type: VoiceType) {
        val voice: Voice = when (type) {
            VoiceType.MALE -> {
                Voice(languageCode = "ko-KR", name = "ko-KR-Chirp3-HD-Charon")
            }

            VoiceType.FEMALE -> {
                Voice(languageCode = "ko-KR", name = "ko-KR-Chirp3-HD-Aoede")
            }
        }

        val service = retrofit.create(TTSService::class.java)

        val response = service.synthesizeSpeech(
            TTSRequest(
                input = Input(text),
                voice = voice,
                audioConfig = AudioConfig("MP3")
            ),
            apiKey = BuildConfig.GOOGLE_API_KEY
        )

        response.enqueue(object: Callback<TTSResponse> {
            override fun onResponse(call: Call<TTSResponse>, response: Response<TTSResponse>) {
                val audioBytes = Base64.decode(response.body()!!.audioContent, Base64.DEFAULT)
                val tempFile = File.createTempFile("tts", "mp3", context.cacheDir)
                FileOutputStream(tempFile).use {
                    it.write(audioBytes)
                }

                MediaPlayer().apply {
                    setDataSource(tempFile.absolutePath)
                    prepare()
                    start()
                }
            }

            override fun onFailure(call: Call<TTSResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}