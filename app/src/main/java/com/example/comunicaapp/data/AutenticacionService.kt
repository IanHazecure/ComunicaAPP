package com.example.comunicaapp.data

import com.example.comunicaapp.model.Usuario

sealed class ResultadoAuth {
    object Exito : ResultadoAuth()
    data class Error(val mensaje: String) : ResultadoAuth()
}

class AutenticacionService(
    private val repositorio: UsuariosRepository = UsuariosRepository.instancia
) {

    fun iniciarSesion(email: String, password: String): ResultadoAuth {
        if (email.isBlank() || password.isBlank()) {
            return ResultadoAuth.Error("Completa todos los campos")
        }
        val usuario = repositorio.validar(email, password)
        return if (usuario == null) {
            ResultadoAuth.Error("Correo o contraseña incorrectos")
        } else {
            ResultadoAuth.Exito
        }
    }

    fun registrarUsuario(
        nombre: String,
        email: String,
        password: String,
        confirmPassword: String,
        aceptaTerminos: Boolean
    ): ResultadoAuth {
        return when {
            repositorio.alcanzoLimite() ->
                ResultadoAuth.Error("Se alcanzó el máximo de ${UsuariosRepository.MAX_USUARIOS} usuarios registrados")
            nombre.isBlank() || email.isBlank() || password.isBlank() ->
                ResultadoAuth.Error("Completa todos los campos")
            password != confirmPassword ->
                ResultadoAuth.Error("Las contraseñas no coinciden")
            repositorio.existeEmail(email) ->
                ResultadoAuth.Error("Ese correo ya está registrado")
            !aceptaTerminos ->
                ResultadoAuth.Error("Debes aceptar los términos y condiciones")
            else -> {
                repositorio.registrar(Usuario(nombre, email, password))
                ResultadoAuth.Exito
            }
        }
    }

    fun solicitarRecuperacion(email: String): ResultadoAuth {
        return when {
            email.isBlank() -> ResultadoAuth.Error("Ingresa tu correo electrónico")
            !repositorio.existeEmail(email) -> ResultadoAuth.Error("No existe una cuenta registrada con ese correo")
            else -> ResultadoAuth.Exito
        }
    }
}
