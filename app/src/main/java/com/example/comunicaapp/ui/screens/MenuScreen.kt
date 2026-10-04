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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "ComunicaApp",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Elige una herramienta para comunicarte",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            MenuActionCard(
                icon = Icons.Filled.RecordVoiceOver,
                title = "Hablar por mí",
                description = "Escribe un mensaje y dilo en voz alta",
                onClick = { navController.navigate(Screen.Hablar.route) },
                highlighted = true
            )
            MenuActionCard(
                icon = Icons.Filled.List,
                title = "Frases rápidas",
                description = "Accede a mensajes listos para usar",
                onClick = { navController.navigate(Screen.FrasesRapidas.route) }
            )
            MenuActionCard(
                icon = Icons.Filled.Hearing,
                title = "Transcribir lo que escuchan",
                description = "Convierte una conversación en texto",
                onClick = { navController.navigate(Screen.Transcripcion.route) }
            )
            MenuActionCard(
                icon = Icons.Filled.People,
                title = "Usuarios registrados",
                description = "Consulta las personas de la aplicación",
                onClick = { navController.navigate(Screen.ListaUsuarios.route) }
            )

            OutlinedButton(
                onClick = { navController.navigate(Screen.Ayuda.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(Icons.Filled.HelpOutline, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("¿Cómo se usa?")
            }

            TextButton(
                onClick = { mostrarConfirmacion = true },
                modifier = Modifier.fillMaxWidth()
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

@Composable
private fun MenuActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    highlighted: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 84.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlighted) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
