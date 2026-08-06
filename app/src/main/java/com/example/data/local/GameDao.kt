package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    // --- GAMES & APKS ---
    @Query("SELECT * FROM games ORDER BY timestamp DESC")
    fun getAllGames(): Flow<List<GameItem>>

    @Query("SELECT * FROM games WHERE isBookmarked = 1 ORDER BY timestamp DESC")
    fun getBookmarkedGames(): Flow<List<GameItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameItem>)

    @Update
    suspend fun updateGame(game: GameItem)

    @Query("DELETE FROM games WHERE id = :id")
    suspend fun deleteGameById(id: Int)

    // --- SPACE FLIGHT SIMULATOR BLUEPRINTS ---
    @Query("SELECT * FROM sfs_blueprints ORDER BY timestamp DESC")
    fun getAllSfsBlueprints(): Flow<List<SfsBlueprintItem>>

    @Query("SELECT * FROM sfs_blueprints WHERE isBookmarked = 1 ORDER BY timestamp DESC")
    fun getBookmarkedBlueprints(): Flow<List<SfsBlueprintItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSfsBlueprint(blueprint: SfsBlueprintItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSfsBlueprints(blueprints: List<SfsBlueprintItem>)

    @Update
    suspend fun updateSfsBlueprint(blueprint: SfsBlueprintItem)

    @Query("DELETE FROM sfs_blueprints WHERE id = :id")
    suspend fun deleteSfsBlueprintById(id: Int)

    // --- LINK CONVERSION LOGS ---
    @Query("SELECT * FROM link_conversion_logs ORDER BY timestamp DESC LIMIT 50")
    fun getLinkLogs(): Flow<List<LinkConversionLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLinkLog(log: LinkConversionLog)

    @Query("DELETE FROM link_conversion_logs")
    suspend fun clearLinkLogs()
}
