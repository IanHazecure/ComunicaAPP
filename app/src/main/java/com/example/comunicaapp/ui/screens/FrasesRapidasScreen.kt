package com.example.comunicaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comunicaapp.data.FrasesRepository
import com.example.comunicaapp.data.LectorVoz
import com.example.comunicaapp.model.Frase

@Composable
fun FrasesRapidasScreen(navController: NavHostController) {
    val context = LocalContext.current
    val lectorVoz = remember { LectorVoz(context) }

    DisposableEffect(Unit) {
        onDispose { lectorVoz.apagar() }
    }

    val frases = FrasesRepository.instancia.obtenerFrases()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp, 24.dp, 24.dp, 8.dp)) {
            Text(
                text = "Frases rápidas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Toca una frase para que el celular la diga en voz alta",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(frases) { frase ->
                FraseCard(
                    frase = frase,
                    onClick = { lectorVoz.decir(frase.texto) }
                )
            }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(50.dp)
        ) {
            Text("Volver")
        }
    }
}

@Composable
private fun FraseCard(frase: Frase, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = frase.categoria,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = frase.texto,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Icon(Icons.Filled.VolumeUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
    }
}
