package com.example.comunicaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comunicaapp.data.LectorVoz
import com.example.comunicaapp.ui.components.ContenedorAdaptativo

@Composable
fun HablarScreen(navController: NavHostController) {
    val context = LocalContext.current
    val lectorVoz = remember { LectorVoz(context) }

    DisposableEffect(Unit) {
        onDispose { lectorVoz.apagar() }
    }

    var texto by remember { mutableStateOf("") }

    ContenedorAdaptativo {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hablar por mí",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Escribe lo que quieres decir y el celular lo dirá en voz alta.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Escribe aquí") },
                minLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { lectorVoz.decir(texto) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(Icons.Filled.VolumeUp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Decir en voz alta")
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
    }
}
