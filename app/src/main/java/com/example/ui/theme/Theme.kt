package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// === Light Theme — Cerah & Bersih dengan sentuhan ungu ===
private val LightColorScheme = lightColorScheme(
    primary            = VividPurple,
    onPrimary          = Color.White,
    primaryContainer   = LightCard,
    onPrimaryContainer = VividPurpleDark,
    secondary          = SkyBlue,
    onSecondary        = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF0369A1),
    tertiary           = MintGreen,
    onTertiary         = Color.White,
    tertiaryContainer  = Color(0xFFD1FAE5),
    onTertiaryContainer = Color(0xFF065F46),
    background         = LightBg,
    onBackground       = TextOnLight,
    surface            = LightSurface,
    onSurface          = TextOnLight,
    surfaceVariant     = LightCard,
    onSurfaceVariant   = TextSecLight,
    outline            = LightBorder,
    error              = HotPink,
    onError            = Color.White,
)

// === Dark Theme — Navy gelap dengan aksen ungu cerah ===
private val DarkColorScheme = darkColorScheme(
    primary            = Color(0xFFA78BFA), // Ungu lebih terang di dark mode
    onPrimary          = Color(0xFF2E1065),
    primaryContainer   = DarkCard,
    onPrimaryContainer = Color(0xFFEDE9FE),
    secondary          = SkyBlue,
    onSecondary        = Color(0xFF0C4A6E),
    secondaryContainer = Color(0xFF0369A1),
    onSecondaryContainer = Color(0xFFE0F2FE),
    tertiary           = MintGreen,
    onTertiary         = Color(0xFF064E3B),
    tertiaryContainer  = Color(0xFF065F46),
    onTertiaryContainer = Color(0xFFD1FAE5),
    background         = DarkBg,
    onBackground       = TextOnDark,
    surface            = DarkSurface,
    onSurface          = TextOnDark,
    surfaceVariant     = DarkCard,
    onSurfaceVariant   = TextSecDark,
    outline            = DarkBorder,
    error              = HotPink,
    onError            = Color.White,
)

@Composable
fun GamePubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

