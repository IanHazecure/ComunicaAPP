package com.example.comunicaapp.data

import androidx.compose.runtime.mutableStateListOf
import com.example.comunicaapp.model.Usuario

class UsuariosRepository private constructor() {

    private val usuarios = mutableStateListOf(
        Usuario("Felipe Ruz", "felipe@correo.com", "1234"),
        Usuario("Camila Rojas", "camila@correo.com", "abcd1234"),
        Usuario("Ignacio Pérez", "ignacio@correo.com", "clave2026"),
        Usuario("Valentina Muñoz", "valentina@correo.com", "pass123"),
        Usuario("Diego Fuentes", "diego@correo.com", "diego2026")
    )

    fun obtenerUsuarios(): List<Usuario> = usuarios

    fun validar(email: String, password: String): Usuario? {
        return usuarios.find { it.email == email && it.password == password }
    }

    fun existeEmail(email: String): Boolean {
        return usuarios.any { it.email == email }
    }

    fun alcanzoLimite(): Boolean {
        return usuarios.size >= MAX_USUARIOS
    }

    fun registrar(usuario: Usuario) {
        usuarios.add(usuario)
    }

    companion object {
        const val MAX_USUARIOS = 5
        val instancia: UsuariosRepository by lazy { UsuariosRepository() }
    }
}
