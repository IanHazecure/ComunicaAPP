package com.example.comunicaapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = BluePrimary,
    onPrimary = SurfaceLight,
    primaryContainer = BlueLight,
    secondary = OrangeAccent,
    onSecondary = SurfaceLight,
    secondaryContainer = OrangeAccentLight,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    error = ErrorRed
)

@Composable
fun ComunicaAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
