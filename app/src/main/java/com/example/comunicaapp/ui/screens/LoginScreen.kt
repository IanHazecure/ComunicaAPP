package com.example.comunicaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comunicaapp.data.AutenticacionService
import com.example.comunicaapp.data.ResultadoAuth
import com.example.comunicaapp.navigation.Screen
import com.example.comunicaapp.ui.components.ContenedorAdaptativo
import com.example.comunicaapp.ui.components.MensajeError

@Composable
fun LoginScreen(navController: NavHostController) {
    val autenticacionService = remember { AutenticacionService() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

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
        Text(
            text = "Comunicación para personas con discapacidad auditiva",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        errorMsg?.let {
            Spacer(modifier = Modifier.height(12.dp))
            MensajeError(it)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                when (val resultado = autenticacionService.iniciarSesion(email, password)) {
                    is ResultadoAuth.Error -> errorMsg = resultado.mensaje
                    is ResultadoAuth.Exito -> {
                        errorMsg = null
                        navController.navigate(Screen.Menu.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.Login, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate(Screen.RecuperarPassword.route) }) {
            Text("¿Olvidaste tu contraseña?")
        }

        TextButton(onClick = { navController.navigate(Screen.Registro.route) }) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
    }
}
