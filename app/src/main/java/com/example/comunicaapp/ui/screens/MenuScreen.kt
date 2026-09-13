package com.example.comunicaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comunicaapp.navigation.Screen
import com.example.comunicaapp.ui.components.ContenedorAdaptativo

@Composable
fun MenuScreen(navController: NavHostController) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    ContenedorAdaptativo {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ComunicaApp",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController.navigate(Screen.Hablar.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.RecordVoiceOver, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Hablar por mí")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.FrasesRapidas.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.List, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Frases rápidas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.Transcripcion.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.Hearing, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Transcribir lo que escuchan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.ListaUsuarios.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.People, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ver usuarios registrados")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { navController.navigate(Screen.Ayuda.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.HelpOutline, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ayuda")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { mostrarConfirmacion = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar sesión")
        }
    }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Seguro que quieres salir de tu cuenta?") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarConfirmacion = false
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Menu.route) { inclusive = true }
                    }
                }) {
                    Text("Sí, salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
