package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// === Gradasi Utama (Ungu → Biru → Cyan) ===
val GradientStart   = Color(0xFF7C3AED) // Ungu vivid
val GradientMid     = Color(0xFF2563EB) // Biru royal
val GradientEnd     = Color(0xFF06B6D4) // Cyan cerah

// === Primary & Aksen ===
val VividPurple     = Color(0xFF8B5CF6) // Primary utama
val VividPurpleDark = Color(0xFF6D28D9) // Primary dark
val SkyBlue         = Color(0xFF38BDF8) // Aksen biru langit
val MintGreen       = Color(0xFF34D399) // Aksen hijau mint
val SunsetOrange    = Color(0xFFFB923C) // Aksen oranye
val HotPink         = Color(0xFFF472B6) // Aksen pink

// === Background Light (Putih bersih + hint ungu) ===
val LightBg         = Color(0xFFF5F3FF) // Putih ungu muda
val LightSurface    = Color(0xFFFFFFFF)
val LightCard       = Color(0xFFEDE9FE) // Ungu sangat muda
val LightBorder     = Color(0xFFDDD6FE)

// === Background Dark ===
val DarkBg          = Color(0xFF1E1B4B) // Navy gelap
val DarkSurface     = Color(0xFF312E81)
val DarkCard        = Color(0xFF3730A3)
val DarkBorder      = Color(0xFF4338CA)

// === Teks ===
val TextOnLight     = Color(0xFF1E1B4B)
val TextSecLight    = Color(0xFF6D28D9)
val TextOnDark      = Color(0xFFF5F3FF)
val TextSecDark     = Color(0xFFC4B5FD)


// === Brush Gradasi Siap Pakai ===
// Cara pakai di Compose:
//   Box(modifier = Modifier.background(brush = GradientPrimary))
//
// import androidx.compose.ui.graphics.Brush
//
// val GradientPrimary = Brush.linearGradient(colors = listOf(GradientStart, GradientMid, GradientEnd))
// val GradientWarm    = Brush.linearGradient(colors = listOf(HotPink, SunsetOrange, Color(0xFFFBBF24)))
// val GradientCool    = Brush.linearGradient(colors = listOf(VividPurple, SkyBlue, MintGreen))
