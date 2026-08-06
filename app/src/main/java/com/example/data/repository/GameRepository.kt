package com.example.data.repository

import com.example.data.local.GameDao
import com.example.data.local.GameItem
import com.example.data.local.LinkConversionLog
import com.example.data.local.SfsBlueprintItem
import com.example.util.LinkConversionResult
import com.example.util.LinkConverter
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    // --- GAMES & APKS ---
    val allGames: Flow<List<GameItem>> = gameDao.getAllGames()
    val bookmarkedGames: Flow<List<GameItem>> = gameDao.getBookmarkedGames()

    suspend fun publishGame(
        title: String,
        developer: String,
        languageEngine: String,
        description: String,
        rawDownloadUrl: String,
        webPreviewUrl: String,
        category: String,
        version: String,
        tags: String,
        sampleCodeSnippet: String
    ): GameItem {
        // Run Link Standardizer on the raw download URL (converts HTML/raw to proper HTTP/HTTPS)
        val conversion = LinkConverter.convertLink(rawDownloadUrl)
        val sanitizedDownloadUrl = if (conversion.convertedUrl.isNotEmpty()) conversion.convertedUrl else rawDownloadUrl

        val sanitizedWebPreviewUrl = if (webPreviewUrl.isNotBlank()) {
            LinkConverter.convertLink(webPreviewUrl).convertedUrl
        } else ""

        val newGame = GameItem(
            title = title,
            developer = developer,
            languageEngine = languageEngine,
            description = description,
            downloadUrl = sanitizedDownloadUrl,
            webPreviewUrl = sanitizedWebPreviewUrl,
            category = category,
            version = if (version.startsWith("v")) version else "v$version",
            rating = 5.0f,
            downloadsCount = 1,
            tags = tags,
            sampleCodeSnippet = sampleCodeSnippet,
            timestamp = System.currentTimeMillis()
        )

        val id = gameDao.insertGame(newGame)
        return newGame.copy(id = id.toInt())
    }

    suspend fun toggleBookmarkGame(game: GameItem) {
        gameDao.updateGame(game.copy(isBookmarked = !game.isBookmarked))
    }

    suspend fun deleteGame(id: Int) {
        gameDao.deleteGameById(id)
    }

    // --- SFS BLUEPRINTS ---
    val allSfsBlueprints: Flow<List<SfsBlueprintItem>> = gameDao.getAllSfsBlueprints()
    val bookmarkedBlueprints: Flow<List<SfsBlueprintItem>> = gameDao.getBookmarkedBlueprints()

    suspend fun uploadSfsBlueprint(
        title: String,
        author: String,
        description: String,
        blueprintData: String,
        rocketCategory: String,
        partsCount: Int,
        massTons: Float,
        thrustTons: Float,
        targetDestination: String
    ): SfsBlueprintItem {
        val newBlueprint = SfsBlueprintItem(
            title = title,
            author = author,
            description = description,
            blueprintData = blueprintData,
            rocketCategory = rocketCategory,
            partsCount = partsCount,
            massTons = massTons,
            thrustTons = thrustTons,
            targetDestination = targetDestination,
            downloadsCount = 1,
            likesCount = 1,
            timestamp = System.currentTimeMillis()
        )

        val id = gameDao.insertSfsBlueprint(newBlueprint)
        return newBlueprint.copy(id = id.toInt())
    }

    suspend fun toggleBookmarkBlueprint(blueprint: SfsBlueprintItem) {
        gameDao.updateSfsBlueprint(blueprint.copy(isBookmarked = !blueprint.isBookmarked))
    }

    suspend fun incrementBlueprintLikes(blueprint: SfsBlueprintItem) {
        gameDao.updateSfsBlueprint(blueprint.copy(likesCount = blueprint.likesCount + 1))
    }

    suspend fun deleteSfsBlueprint(id: Int) {
        gameDao.deleteSfsBlueprintById(id)
    }

    // --- LINK CONVERTER & LOGS ---
    val linkConversionLogs: Flow<List<LinkConversionLog>> = gameDao.getLinkLogs()

    suspend fun processAndLogLink(rawText: String): LinkConversionResult {
        val result = LinkConverter.convertLink(rawText)
        if (result.convertedUrl.isNotEmpty()) {
            gameDao.insertLinkLog(
                LinkConversionLog(
                    originalText = result.originalText,
                    convertedUrl = result.convertedUrl,
                    protocol = result.protocol,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
        return result
    }

    suspend fun clearLinkLogs() {
        gameDao.clearLinkLogs()
    }
}
