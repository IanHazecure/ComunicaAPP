package com.example.comunicaapp.data

import android.util.Log
import com.example.comunicaapp.model.Usuario

sealed class ResultadoAuth {
    object Exito : ResultadoAuth()
    data class Error(val mensaje: String) : ResultadoAuth()
}

class AutenticacionService(
    private val repositorio: UsuariosRepository = UsuariosRepository.instancia
) {

    fun iniciarSesion(
        email: String,
        password: String,
        onResult: (ResultadoAuth) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onResult(ResultadoAuth.Error("Completa todos los campos"))
            return
        }
        repositorio.validar(email, password, { usuario ->
            onResult(
                if (usuario == null) ResultadoAuth.Error("Correo o contraseña incorrectos")
                else ResultadoAuth.Exito
            )
        }, { error ->
            Log.e("AutenticacionService", "Error al iniciar sesión", error)
            val detalle = error.localizedMessage ?: error.message ?: error.toString()
            onResult(ResultadoAuth.Error("Error de base de datos: $detalle"))
        })
    }

    fun registrarUsuario(
        nombre: String,
        email: String,
        password: String,
        confirmPassword: String,
        aceptaTerminos: Boolean,
        onResult: (ResultadoAuth) -> Unit
    ) {
        when {
            nombre.isBlank() || email.isBlank() || password.isBlank() ->
                onResult(ResultadoAuth.Error("Completa todos los campos"))
            password != confirmPassword ->
                onResult(ResultadoAuth.Error("Las contraseñas no coinciden"))
            !aceptaTerminos ->
                onResult(ResultadoAuth.Error("Debes aceptar los términos y condiciones"))
            else -> repositorio.existeEmail(email, { existe ->
                if (existe) {
                    onResult(ResultadoAuth.Error("Ese correo ya está registrado"))
                } else {
                    repositorio.registrar(Usuario(nombre.trim(), email.trim(), password), {
                        onResult(ResultadoAuth.Exito)
                    }, { error ->
                        Log.e("AutenticacionService", "Error al registrar usuario", error)
                        val detalle = error.localizedMessage ?: error.message ?: error.toString()
                        onResult(ResultadoAuth.Error("No se pudo guardar el usuario: $detalle"))
                    })
                }
            }, { error ->
                Log.e("AutenticacionService", "Error al verificar email", error)
                val detalle = error.localizedMessage ?: error.message ?: error.toString()
                onResult(ResultadoAuth.Error("Error de base de datos: $detalle"))
            })
        }
    }

    fun solicitarRecuperacion(
        email: String,
        onResult: (ResultadoAuth) -> Unit
    ) {
        if (email.isBlank()) {
            onResult(ResultadoAuth.Error("Ingresa tu correo electrónico"))
            return
        }
        repositorio.existeEmail(email, { existe ->
            onResult(
                if (existe) ResultadoAuth.Exito
                else ResultadoAuth.Error("No existe una cuenta registrada con ese correo")
            )
        }, { error ->
            Log.e("AutenticacionService", "Error al solicitar recuperación", error)
            val detalle = error.localizedMessage ?: error.message ?: error.toString()
            onResult(ResultadoAuth.Error("Error de base de datos: $detalle"))
        })
    }
}
