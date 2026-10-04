package com.example.comunicaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comunicaapp.ui.components.ContenedorAdaptativo

@Composable
fun AyudaScreen(navController: NavHostController) {
    ContenedorAdaptativo {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ayuda",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        AyudaPaso(
            titulo = "¿Cómo hablo con alguien que no sabe lengua de señas?",
            texto = "Ve a \"Hablar por mí\", escribe lo que quieres decir y toca " +
                "\"Decir en voz alta\". El celular lo dirá por ti."
        )
        AyudaPaso(
            titulo = "¿Cómo uso una frase que ya tengo lista?",
            texto = "Ve a \"Frases rápidas\" y toca cualquier tarjeta para que " +
                "se diga en voz alta al instante."
        )
        AyudaPaso(
            titulo = "¿Cómo sé qué me está diciendo la otra persona?",
            texto = "Ve a \"Transcribir lo que escuchan\", toca el botón del " +
                "micrófono y deja que la otra persona hable. Su voz aparecerá " +
                "como texto en la pantalla."
        )
        AyudaPaso(
            titulo = "¿Cómo salgo de la aplicación?",
            texto = "En el menú principal, toca el botón \"Cerrar sesión\"."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Volver")
        }
    }
    }
}

@Composable
private fun AyudaPaso(titulo: String, texto: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = texto, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
