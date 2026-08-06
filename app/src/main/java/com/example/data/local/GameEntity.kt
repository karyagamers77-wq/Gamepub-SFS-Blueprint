package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val developer: String,
    val languageEngine: String, // Unity, Godot, HTML5, Kotlin, Python, Construct, Unreal, etc.
    val description: String,
    val downloadUrl: String, // Standardized HTTP / HTTPS URL
    val webPreviewUrl: String = "",
    val category: String = "Game", // Game, APK Tool, Web Game
    val version: String = "v1.0.0",
    val rating: Float = 4.8f,
    val downloadsCount: Int = 120,
    val tags: String = "Action, Indie",
    val sampleCodeSnippet: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isBookmarked: Boolean = false
)

@Entity(tableName = "sfs_blueprints")
data class SfsBlueprintItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val description: String,
    val blueprintData: String, // Formatted SFS blueprint text/JSON
    val rocketCategory: String = "Rockets", // Rockets, Space Stations, Landers, Satellites, Experimental
    val partsCount: Int = 45,
    val massTons: Float = 120.5f,
    val thrustTons: Float = 240.0f,
    val targetDestination: String = "Moon (Orbit/Landing)",
    val downloadsCount: Int = 350,
    val likesCount: Int = 88,
    val isBookmarked: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "link_conversion_logs")
data class LinkConversionLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val originalText: String,
    val convertedUrl: String,
    val protocol: String, // "HTTP" or "HTTPS"
    val timestamp: Long = System.currentTimeMillis()
)
