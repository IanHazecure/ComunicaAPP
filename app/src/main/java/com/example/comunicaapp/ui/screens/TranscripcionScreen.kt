package com.example.comunicaapp.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.comunicaapp.ui.components.ContenedorAdaptativo
import com.example.comunicaapp.ui.components.MensajeError
import java.util.Locale

@Composable
fun TranscripcionScreen(navController: NavHostController) {
    val context = LocalContext.current
    var textoTranscrito by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val lanzadorReconocimiento = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        if (resultado.resultCode == Activity.RESULT_OK) {
            val texto = resultado.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
            if (texto != null) {
                textoTranscrito = texto
                errorMsg = null
            } else {
                errorMsg = "No se pudo reconocer el audio, intenta de nuevo"
            }
        }
    }

    fun iniciarReconocimiento() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("es", "CL").toString())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")
        }
        try {
            lanzadorReconocimiento.launch(intent)
        } catch (e: Exception) {
            errorMsg = "Este dispositivo no tiene reconocimiento de voz disponible"
        }
    }

    val lanzadorPermiso = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            iniciarReconocimiento()
        } else {
            errorMsg = "Se necesita permiso de micrófono para transcribir"
        }
    }

    ContenedorAdaptativo {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Transcribir lo que escuchan",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Toca el botón, deja que la otra persona hable, y su voz " +
                "aparecerá aquí como texto.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val permisoConcedido = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED

                if (permisoConcedido) {
                    iniciarReconocimiento()
                } else {
                    lanzadorPermiso.launch(Manifest.permission.RECORD_AUDIO)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(Icons.Filled.Mic, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Escuchar y transcribir")
        }

        errorMsg?.let {
            Spacer(modifier = Modifier.height(16.dp))
            MensajeError(it)
        }

        if (textoTranscrito.isNotBlank()) {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Transcripción",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = textoTranscrito,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
    }
}
