package com.example.comunicaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comunicaapp.ui.screens.AyudaScreen
import com.example.comunicaapp.ui.screens.FrasesRapidasScreen
import com.example.comunicaapp.ui.screens.HablarScreen
import com.example.comunicaapp.ui.screens.ListaUsuariosScreen
import com.example.comunicaapp.ui.screens.LoginScreen
import com.example.comunicaapp.ui.screens.MenuScreen
import com.example.comunicaapp.ui.screens.RecuperarPasswordScreen
import com.example.comunicaapp.ui.screens.RegistroScreen
import com.example.comunicaapp.ui.screens.TranscripcionScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Registro.route) {
            RegistroScreen(navController)
        }

        composable(Screen.RecuperarPassword.route) {
            RecuperarPasswordScreen(navController)
        }

        composable(Screen.Menu.route) {
            MenuScreen(navController)
        }

        composable(Screen.Hablar.route) {
            HablarScreen(navController)
        }

        composable(Screen.FrasesRapidas.route) {
            FrasesRapidasScreen(navController)
        }

        composable(Screen.Transcripcion.route) {
            TranscripcionScreen(navController)
        }

        composable(Screen.ListaUsuarios.route) {
            ListaUsuariosScreen(navController)
        }

        composable(Screen.Ayuda.route) {
            AyudaScreen(navController)
        }
    }
}
