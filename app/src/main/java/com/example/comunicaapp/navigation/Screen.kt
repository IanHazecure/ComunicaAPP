package com.example.comunicaapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registro : Screen("registro")
    object RecuperarPassword : Screen("recuperar_password")
    object Menu : Screen("menu")
    object Hablar : Screen("hablar")
    object FrasesRapidas : Screen("frases_rapidas")
    object Transcripcion : Screen("transcripcion")
    object ListaUsuarios : Screen("lista_usuarios")
    object Ayuda : Screen("ayuda")
}
