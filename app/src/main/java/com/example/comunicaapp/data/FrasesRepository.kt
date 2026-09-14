package com.example.comunicaapp.data

import com.example.comunicaapp.model.Frase

class FrasesRepository private constructor() {

    private val frases = listOf(
        Frase(1, "Saludos", "Hola, soy sordo o sorda. Nos podemos comunicar escribiendo."),
        Frase(2, "Saludos", "Buenos días, ¿cómo estás?"),
        Frase(3, "Ayuda", "¿Puedes escribirlo en el celular, por favor?"),
        Frase(4, "Ayuda", "¿Puedes hablar un poco más despacio mirándome de frente?"),
        Frase(5, "Cortesía", "Muchas gracias por tu paciencia."),
        Frase(6, "Cortesía", "Perdona la demora, estoy escribiendo.")
    )

    fun obtenerFrases(): List<Frase> = frases

    companion object {
        val instancia: FrasesRepository by lazy { FrasesRepository() }
    }
}
