package com.example.vozdux.presenter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF343a40),
    secondary = Color.Black,
    onBackground = Color(0xFFf8f9fa), // Header text color
    onSecondary = Color(0xFFdee2e6), // Primary text color
    primary = Color(0xFF613EEA),
    onPrimary = Color.White,
    tertiary = Color(0xFFFF7D00)
)

private val LightColorScheme = lightColorScheme(
    background = Color(0xFFf8f9fa),
    secondary = Color.White,
    onBackground = Color(0xFF171D33), // Header text color
    onSecondary = Color(0xFF757F8C), // Primary text color
    primary = Color(0xFF613EEA),
    onPrimary = Color.White,
    tertiary = Color(0xFFFF7D00)
)

@Composable
fun VozduxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ FALSE
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = colorScheme.background,
        darkIcons = !darkTheme
    )

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> {
            DarkColorScheme
        }

        else -> {
            LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
