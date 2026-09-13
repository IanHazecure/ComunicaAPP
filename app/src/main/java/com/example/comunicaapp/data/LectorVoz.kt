package com.example.comunicaapp.data

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class LectorVoz(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var listo = false

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale("es", "CL")
            listo = true
        }
    }

    fun decir(texto: String) {
        if (listo && texto.isNotBlank()) {
            tts?.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun apagar() {
        tts?.stop()
        tts?.shutdown()
    }
}
