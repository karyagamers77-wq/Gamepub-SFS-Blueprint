package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.PurpleAccent

@Composable
fun DevGuidesScreen(
    onCopyToast: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val guides = listOf(
        DevGuideItem(
            title = "1. Unity (C#) - Ekspor Android APK & Web",
            engine = "Unity",
            summary = "Panduan melakukan build APK Android atau WebGL di Unity Engine dan menyiapkan tautan publikasi.",
            codeSnippet = """
                // Unity Sample C# Script untuk membuka link game
                using UnityEngine;

                public class GameLinkHandler : MonoBehaviour {
                    public string gameDownloadUrl = "https://yourdomain.com/mygame.apk";

                    public void OpenGameLink() {
                        Application.OpenURL(gameDownloadUrl);
                    }
                }
            """.trimIndent(),
            steps = listOf(
                "Di Unity, buka File -> Build Settings -> Pilih Android.",
                "Di Player Settings -> Identification, tentukan Package Name (misal com.yourname.game).",
                "Pastikan Target API Level diatur ke versi terbaru.",
                "Klik Build untuk menghasilkan file .apk, lalu unggah file ke server/cloud.",
                "Salin tautan unduh lalu gunakan fitur 'Link Converter' di aplikasi ini untuk memastikan tautan HTTP/HTTPS valid!"
            )
        ),
        DevGuideItem(
            title = "2. Godot Engine (GDScript) - Ekspor APK & HTML5",
            engine = "Godot",
            summary = "Petunjuk ekspor game 2D/3D buatan Godot Engine ke format Android APK dan Web HTML5.",
            codeSnippet = """
                # Godot GDScript - Membuka tautan publikasi HTTP/HTTPS
                extends Node

                var my_apk_url = "https://indiedev.id/godot_game.apk"

                func _on_download_button_pressed():
                    OS.shell_open(my_apk_url)
            """.trimIndent(),
            steps = listOf(
                "Buka Project -> Export di Godot Engine.",
                "Tambahkan preset 'Android' atau 'Web (HTML5)'.",
                "Unduh Android SDK & Keystore jika pertama kali melakukan ekspor APK.",
                "Ekspor file .apk atau folder HTML5 .zip.",
                "Publikasikan tautan hasil ekspor ke GamePub & SFS Hub!"
            )
        ),
        DevGuideItem(
            title = "3. HTML5 / JavaScript Web Games",
            engine = "HTML5",
            summary = "Cara mempublikasikan game berbasis browser HTML5, CSS, dan JavaScript agar dapat dimainkan di HP & PC.",
            codeSnippet = """
                <!-- Template Dasar HTML5 Web Game -->
                <!DOCTYPE html>
                <html>
                <head>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>My HTML5 Game</title>
                </head>
                <body style="margin:0; background:#000;">
                  <canvas id="gameCanvas"></canvas>
                  <script>
                    const canvas = document.getElementById('gameCanvas');
                    const ctx = canvas.getContext('2d');
                    // Tulis logika game JavaScript di sini
                  </script>
                </body>
                </html>
            """.trimIndent(),
            steps = listOf(
                "Buat file index.html sebagai file utama game.",
                "Gunakan responsive canvas atau Meta Viewport agar mendukung layar HP dan Komputer.",
                "Unggah file ke hosting web gratis seperti GitHub Pages, Netlify, atau Vercel.",
                "Salin URL HTTPS game HTML5 lalu publikasikan ke kategori 'Web Game'!"
            )
        ),
        DevGuideItem(
            title = "4. Python (Pygame) - Kompilasi Ke Android APK",
            engine = "Python",
            summary = "Langkah mengubah script game Python Pygame menjadi aplikasi Android APK menggunakan Buildozer / Kivy.",
            codeSnippet = """
                # Python Pygame - Struktur Utama Game
                import pygame
                pygame.init()
                screen = pygame.display.set_mode((800, 600))
                running = True
                while running:
                    for event in pygame.event.get():
                        if event.type == pygame.QUIT:
                            running = False
                    screen.fill((15, 23, 42))
                    pygame.display.flip()
            """.trimIndent(),
            steps = listOf(
                "Install Buildozer: `pip install buildozer`",
                "Jalankan `buildozer init` di direktori proyek Python Anda.",
                "Buka file `buildozer.spec` dan sesuaikan nama aplikasi, paket, dan kebutuhan requirements (pygame, kivy).",
                "Jalankan `buildozer -v android debug` untuk menghasilkan file .apk di folder `bin/`.",
                "Unggah file .apk dan publikasikan di platform ini!"
            )
        ),
        DevGuideItem(
            title = "5. Kotlin / Android Java (Native)",
            engine = "Kotlin",
            summary = "Membangun game atau aplikasi native Android menggunakan Android Studio dan Jetpack Compose Canvas.",
            codeSnippet = """
                // Kotlin Compose Canvas Game Loop
                @Composable
                fun SimpleNativeGame() {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawRect(color = Color.Cyan)
                    }
                }
            """.trimIndent(),
            steps = listOf(
                "Buka Android Studio -> Build -> Generate Signed Bundle / APK.",
                "Pilih APK -> Buat atau pilih Keystore release.",
                "Pilih build variant 'release' dan klik Finish.",
                "Temukan file app-release.apk di folder project `app/release/`.",
                "Publikasikan APK buatanmu langsung ke GamePub & SFS Hub!"
            )
        ),
        DevGuideItem(
            title = "6. Space Flight Simulator (SFS) Blueprint Guide",
            engine = "SFS",
            summary = "Panduan cara membuat dan memasukkan file .txt / JSON blueprint roket ke dalam game Space Flight Simulator.",
            codeSnippet = """
                // Format JSON Blueprint Space Flight Simulator (.txt)
                {
                  "centerData": { "x": 0.0, "y": 0.0 },
                  "parts": [
                    { "name": "Fairing Cone", "position": { "x": 0.0, "y": 10.0 } },
                    { "name": "Fuel Tank Big", "position": { "x": 0.0, "y": 0.0 } }
                  ],
                  "stages": 2,
                  "sfsVersion": "1.5.10"
                }
            """.trimIndent(),
            steps = listOf(
                "Salin kode blueprint dari tab 'SFS Blueprint' di aplikasi ini.",
                "Buka file manager di HP dan masuk ke lokasi folder SFS:\nAndroid/data/com.StefMorojna.SpaceFlightSimulator/files/Saving/Blueprints/",
                "Buat folder baru dengan nama roket pilihanmu.",
                "Di dalam folder tersebut, buat file baru bernama 'Blueprint.txt' lalu tempelkan kode yang telah disalin.",
                "Buka game Space Flight Simulator -> Buka menu Blueprint -> Load Blueprint!"
            )
        )
    )

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        // Header Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Code,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Panduan Publikasi Multi-Bahasa",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Dukungan publikasi lengkap untuk Unity, Godot, HTML5, Python, Kotlin, dan Space Flight Simulator.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Guide Expandable List
        items(guides, key = { it.title }) { guide ->
            DevGuideExpandableCard(
                guide = guide,
                onCopyCode = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Dev Guide Code", guide.codeSnippet)
                    clipboard.setPrimaryClip(clip)
                    onCopyToast()
                }
            )
        }
    }
}

data class DevGuideItem(
    val title: String,
    val engine: String,
    val summary: String,
    val codeSnippet: String,
    val steps: List<String>
)

@Composable
private fun DevGuideExpandableCard(
    guide: DevGuideItem,
    onCopyCode: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = getEngineThemeColor(guide.engine).copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = guide.engine,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = getEngineThemeColor(guide.engine),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = guide.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Buka Detail Panduan"
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = guide.summary,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = "Langkah-Langkah Publikasi:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    guide.steps.forEach { step ->
                        Text(
                            text = "• $step",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contoh Kode / Script Integration:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        IconButton(onClick = onCopyCode, modifier = Modifier.size(24.dp)) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Salin Kode",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF0F172A),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = guide.codeSnippet,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = CyanPrimary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun getEngineThemeColor(engine: String): Color {
    return when (engine) {
        "Unity" -> CyanPrimary
        "Godot" -> Color(0xFF478CBF)
        "HTML5" -> Color(0xFFE44D26)
        "Python" -> GoldSecondary
        "Kotlin" -> PurpleAccent
        "SFS" -> GoldSecondary
        else -> CyanPrimary
    }
}
