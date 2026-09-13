package com.example.comunicaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comunicaapp.data.AutenticacionService
import com.example.comunicaapp.data.ResultadoAuth
import com.example.comunicaapp.ui.components.ContenedorAdaptativo
import com.example.comunicaapp.ui.components.MensajeError
import com.example.comunicaapp.ui.components.MensajeExito

@Composable
fun RecuperarPasswordScreen(navController: NavHostController) {
    val autenticacionService = remember { AutenticacionService() }

    var email by remember { mutableStateOf("") }
    var mensajeEnviado by remember { mutableStateOf(false) }
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
            text = "Recuperar contraseña",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ingresa tu correo y te enviaremos las instrucciones " +
                "para restablecer tu contraseña.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                mensajeEnviado = false
                errorMsg = null
            },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                when (val resultado = autenticacionService.solicitarRecuperacion(email)) {
                    is ResultadoAuth.Error -> {
                        errorMsg = resultado.mensaje
                        mensajeEnviado = false
                    }
                    is ResultadoAuth.Exito -> {
                        errorMsg = null
                        mensajeEnviado = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enviar instrucciones")
        }

        errorMsg?.let {
            Spacer(modifier = Modifier.height(12.dp))
            MensajeError(it)
        }

        if (mensajeEnviado) {
            Spacer(modifier = Modifier.height(12.dp))
            MensajeExito("Se envió un correo a $email con las instrucciones para recuperar tu contraseña")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver a iniciar sesión")
        }
    }
    }
}
