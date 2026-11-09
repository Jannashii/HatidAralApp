package com.example.hatidaralapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TODO: replace with the exact hex values from Figma Dev Mode
val Navy = Color(0xFF0B2E55)
val Mint = Color(0xFFE3EEEA)
val Green = Color(0xFF2E9E57)
val Orange = Color(0xFFE08A2E)
val Red = Color(0xFFD64545)
val Blue = Color(0xFF3B7DD8)
val TextDark = Color(0xFF1B1F23)
val TextMuted = Color(0xFF55636B)

@Composable
fun HatidAralTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Navy,
            onPrimary = Color.White,
            background = Mint,
            surface = Color.White,
            onSurface = TextDark
        ),
        content = content
    )
}
