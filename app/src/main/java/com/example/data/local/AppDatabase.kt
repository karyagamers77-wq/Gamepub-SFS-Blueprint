package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [GameItem::class, SfsBlueprintItem::class, LinkConversionLog::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gamepub_sfs_db"
                )
                .addCallback(DatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialData(database.gameDao())
                    }
                }
            }
        }

        private suspend fun populateInitialData(dao: GameDao) {
            val initialGames = listOf(
                GameItem(
                    title = "Astro Runner 3D",
                    developer = "Skyline Studio",
                    languageEngine = "Unity (C#)",
                    description = "Game endless runner 3D luar angkasa dengan grafik memukau dan kontrol giroskopik responsif.",
                    downloadUrl = "https://cdn.gamepub.dev/apks/astrorunner_v1.2.apk",
                    webPreviewUrl = "https://play.gamepub.dev/astrorunner",
                    category = "Game",
                    version = "v1.2.0",
                    rating = 4.9f,
                    downloadsCount = 1450,
                    tags = "3D, Unity, Runner, Action",
                    sampleCodeSnippet = """
                        using UnityEngine;
                        public class PlayerController : MonoBehaviour {
                            public float speed = 10f;
                            void Update() {
                                transform.Translate(Vector3.forward * speed * Time.deltaTime);
                            }
                        }
                    """.trimIndent()
                ),
                GameItem(
                    title = "Galactic Explorer 2D",
                    developer = "IndieDev Nusantara",
                    languageEngine = "Godot (GDScript)",
                    description = "Petualangan penjelajahan galaksi 2D buatan sendiri. Mendukung ekspor HTML5 dan Android APK.",
                    downloadUrl = "http://download.indiedev.id/games/galactic_explorer.apk",
                    webPreviewUrl = "https://indiedev.id/webgames/galactic",
                    category = "Game",
                    version = "v2.0.1",
                    rating = 4.7f,
                    downloadsCount = 890,
                    tags = "2D, Godot, Sci-Fi, Exploration",
                    sampleCodeSnippet = """
                        extends CharacterBody2D
                        const SPEED = 300.0
                        func _physics_process(delta):
                            var direction = Input.get_vector("ui_left", "ui_right", "ui_up", "ui_down")
                            velocity = direction * SPEED
                            move_and_slide()
                    """.trimIndent()
                ),
                GameItem(
                    title = "Neon Space Invaders HTML5",
                    developer = "WebGame Craft",
                    languageEngine = "HTML5 / JS",
                    description = "Game retro arcade HTML5 yang bisa dimainkan langsung di browser HP maupun PC tanpa instalasi.",
                    downloadUrl = "https://html5.gamepub.dev/space-invaders/index.html",
                    webPreviewUrl = "https://html5.gamepub.dev/space-invaders/index.html",
                    category = "Web Game",
                    version = "v1.0.5",
                    rating = 4.6f,
                    downloadsCount = 2300,
                    tags = "HTML5, Retro, Arcade, Browser",
                    sampleCodeSnippet = """
                        const canvas = document.getElementById('game');
                        const ctx = canvas.getContext('2d');
                        function gameLoop() {
                            ctx.clearRect(0, 0, canvas.width, canvas.height);
                            // Draw player ship
                            requestAnimationFrame(gameLoop);
                        }
                    """.trimIndent()
                ),
                GameItem(
                    title = "Orbit Physics Simulator",
                    developer = "Python Space Lab",
                    languageEngine = "Python (Pygame)",
                    description = "Simulasi hukum gravitasi Newton dan orbit planet interaktif dikompilasi ke APK Android via Kivy.",
                    downloadUrl = "https://python.gamelab.org/downloads/orbit_sim.apk",
                    webPreviewUrl = "https://python.gamelab.org/demo",
                    category = "APK Tool",
                    version = "v1.1.0",
                    rating = 4.8f,
                    downloadsCount = 610,
                    tags = "Python, Physics, Gravity, Education",
                    sampleCodeSnippet = """
                        import pygame
                        import math
                        pygame.init()
                        screen = pygame.display.set_mode((800, 600))
                        # Gravity simulation loop
                    """.trimIndent()
                ),
                GameItem(
                    title = "Kotlin Jetpack Hero APK",
                    developer = "Android Dev ID",
                    languageEngine = "Kotlin / Android Java",
                    description = "Aplikasi game strategi lokal penuh menggunakan Kotlin Compose dan Canvas API tanpa engine eksternal.",
                    downloadUrl = "https://github.com/android-dev-id/jetpack-hero/releases/download/v1.0/app-release.apk",
                    webPreviewUrl = "",
                    category = "Game",
                    version = "v1.0.0",
                    rating = 5.0f,
                    downloadsCount = 520,
                    tags = "Kotlin, Native, Jetpack Compose, Strategy",
                    sampleCodeSnippet = """
                        @Composable
                        fun GameCanvas() {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawCircle(color = Color.Cyan, radius = 50f)
                            }
                        }
                    """.trimIndent()
                )
            )

            val initialSfsBlueprints = listOf(
                SfsBlueprintItem(
                    title = "Saturn V Moon Expedition Rocket",
                    author = "Commander Rocketry",
                    description = "Blueprint roket Saturn V lengkap 3 stage dengan Lunar Module & Apollo Command Capsule. Siap untuk misi pendaratan Bulan!",
                    blueprintData = """
                        {
                          "centerData": { "x": 0.0, "y": 12.5 },
                          "parts": [
                            { "name": "Fairing Cone", "position": { "x": 0.0, "y": 42.0 }, "color": "White" },
                            { "name": "Apollo Command Module", "position": { "x": 0.0, "y": 38.0 }, "heatShield": true },
                            { "name": "Lunar Lander Module", "position": { "x": 0.0, "y": 32.0 }, "legs": 4 },
                            { "name": "S-IVB Third Stage", "position": { "x": 0.0, "y": 22.0 }, "engine": "J-2 Engine" },
                            { "name": "S-II Second Stage", "position": { "x": 0.0, "y": 8.0 }, "engine": "5x J-2 Engines" },
                            { "name": "S-IC First Stage Tank", "position": { "x": 0.0, "y": -15.0 }, "fuel": "RP-1/LOX" },
                            { "name": "F-1 Booster Cluster", "position": { "x": 0.0, "y": -35.0 }, "engine": "5x F-1 Engines" }
                          ],
                          "stages": 3,
                          "sfsVersion": "1.5.10"
                        }
                    """.trimIndent(),
                    rocketCategory = "Rockets",
                    partsCount = 68,
                    massTons = 2950.0f,
                    thrustTons = 3500.0f,
                    targetDestination = "Moon (Landing & Return)",
                    downloadsCount = 1840,
                    likesCount = 420
                ),
                SfsBlueprintItem(
                    title = "International Space Station Alpha Core",
                    author = "AstroBuilder99",
                    description = "Modul utama stasiun luar angkasa dilengkapi panel surya raksasa, docking port ganda, dan modul laboratorium.",
                    blueprintData = """
                        {
                          "centerData": { "x": 0.0, "y": 0.0 },
                          "parts": [
                            { "name": "Central Hub Tunnel", "position": { "x": 0.0, "y": 0.0 } },
                            { "name": "Large Solar Array Left", "position": { "x": -18.0, "y": 0.0 } },
                            { "name": "Large Solar Array Right", "position": { "x": 18.0, "y": 0.0 } },
                            { "name": "Science Lab Module", "position": { "x": 0.0, "y": 6.0 } },
                            { "name": "RCS Control Thrusters", "position": { "x": 0.0, "y": -6.0 } }
                          ],
                          "stages": 1,
                          "sfsVersion": "1.5.10"
                        }
                    """.trimIndent(),
                    rocketCategory = "Space Stations",
                    partsCount = 52,
                    massTons = 185.0f,
                    thrustTons = 40.0f,
                    targetDestination = "Low Earth Orbit (400km)",
                    downloadsCount = 1120,
                    likesCount = 310
                ),
                SfsBlueprintItem(
                    title = "Falcon Heavy Reusable System",
                    author = "SpaceX Enthusiast",
                    description = "Roket Falcon Heavy dengan 3 booster F-9. Dilengkapi grid fins dan landing legs untuk mendarat kembali di darat!",
                    blueprintData = """
                        {
                          "centerData": { "x": 0.0, "y": 10.0 },
                          "parts": [
                            { "name": "Payload Fairing", "position": { "x": 0.0, "y": 30.0 } },
                            { "name": "Center Core Stage", "position": { "x": 0.0, "y": 5.0 } },
                            { "name": "Side Booster Left", "position": { "x": -4.0, "y": 0.0 }, "gridFins": true, "legs": true },
                            { "name": "Side Booster Right", "position": { "x": 4.0, "y": 0.0 }, "gridFins": true, "legs": true },
                            { "name": "Merlin Engine Cluster 27x", "position": { "x": 0.0, "y": -20.0 } }
                          ],
                          "stages": 2,
                          "sfsVersion": "1.5.10"
                        }
                    """.trimIndent(),
                    rocketCategory = "Rockets",
                    partsCount = 84,
                    massTons = 1420.0f,
                    thrustTons = 2280.0f,
                    targetDestination = "Mars / GEO Orbit",
                    downloadsCount = 2100,
                    likesCount = 560
                ),
                SfsBlueprintItem(
                    title = "Lunar Lander & Rover Delivery",
                    author = "MoonWalker ID",
                    description = "Modul lander Bulan super efisien dilengkapi rover eksplorasi, antena komunikasi, dan mesin pendarat hipergoolik.",
                    blueprintData = """
                        {
                          "centerData": { "x": 0.0, "y": 0.0 },
                          "parts": [
                            { "name": "Command Pod Mini", "position": { "x": 0.0, "y": 4.0 } },
                            { "name": "Landing Legs HD", "position": { "x": 0.0, "y": 0.0 }, "legs": 4 },
                            { "name": "Surface Rover Dropper", "position": { "x": 0.0, "y": -2.0 } },
                            { "name": "High-Efficiency Engine", "position": { "x": 0.0, "y": -5.0 } }
                          ],
                          "stages": 1,
                          "sfsVersion": "1.5.10"
                        }
                    """.trimIndent(),
                    rocketCategory = "Landers & Rovers",
                    partsCount = 32,
                    massTons = 42.0f,
                    thrustTons = 85.0f,
                    targetDestination = "Moon Surface",
                    downloadsCount = 980,
                    likesCount = 245
                )
            )

            dao.insertGames(initialGames)
            dao.insertSfsBlueprints(initialSfsBlueprints)
        }
    }
}
