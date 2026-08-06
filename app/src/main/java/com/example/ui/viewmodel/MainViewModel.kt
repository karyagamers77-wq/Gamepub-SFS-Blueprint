package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.GameItem
import com.example.data.local.LinkConversionLog
import com.example.data.local.SfsBlueprintItem
import com.example.data.repository.GameRepository
import com.example.util.LinkConversionResult
import com.example.util.LinkConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val title: String, val iconName: String) {
    GAMES_HUB("Game & APK", "gamepad"),
    SFS_BLUEPRINTS("SFS Blueprint", "rocket"),
    LINK_CONVERTER("Link Converter", "link"),
    DEV_GUIDE("Panduan Bahasa", "code")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = GameRepository(database.gameDao())
    }

    // Tab Navigation
    private val _currentTab = MutableStateFlow(AppTab.GAMES_HUB)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // Search & Filter state for Games
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val _selectedLanguageFilter = MutableStateFlow("Semua")
    val selectedLanguageFilter: StateFlow<String> = _selectedLanguageFilter.asStateFlow()

    fun updateLanguageFilter(language: String) {
        _selectedLanguageFilter.value = language
    }

    private val _selectedCategoryFilter = MutableStateFlow("Semua")
    val selectedCategoryFilter: StateFlow<String> = _selectedCategoryFilter.asStateFlow()

    fun updateCategoryFilter(category: String) {
        _selectedCategoryFilter.value = category
    }

    // Filtered Games Flow
    val filteredGames: StateFlow<List<GameItem>> = combine(
        repository.allGames,
        _searchQuery,
        _selectedLanguageFilter,
        _selectedCategoryFilter
    ) { games, query, language, category ->
        games.filter { game ->
            val matchesQuery = query.isBlank() ||
                    game.title.contains(query, ignoreCase = true) ||
                    game.developer.contains(query, ignoreCase = true) ||
                    game.tags.contains(query, ignoreCase = true) ||
                    game.languageEngine.contains(query, ignoreCase = true)

            val matchesLanguage = language == "Semua" ||
                    game.languageEngine.contains(language, ignoreCase = true)

            val matchesCategory = category == "Semua" ||
                    game.category.equals(category, ignoreCase = true)

            matchesQuery && matchesLanguage && matchesCategory
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Filter state for SFS Blueprints
    private val _selectedSfsCategory = MutableStateFlow("Semua")
    val selectedSfsCategory: StateFlow<String> = _selectedSfsCategory.asStateFlow()

    fun updateSfsCategory(category: String) {
        _selectedSfsCategory.value = category
    }

    val filteredSfsBlueprints: StateFlow<List<SfsBlueprintItem>> = combine(
        repository.allSfsBlueprints,
        _searchQuery,
        _selectedSfsCategory
    ) { blueprints, query, category ->
        blueprints.filter { bp ->
            val matchesQuery = query.isBlank() ||
                    bp.title.contains(query, ignoreCase = true) ||
                    bp.author.contains(query, ignoreCase = true) ||
                    bp.targetDestination.contains(query, ignoreCase = true)

            val matchesCategory = category == "Semua" ||
                    bp.rocketCategory.contains(category, ignoreCase = true)

            matchesQuery && matchesCategory
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Link Conversion state
    private val _linkInput = MutableStateFlow("")
    val linkInput: StateFlow<String> = _linkInput.asStateFlow()

    private val _linkResult = MutableStateFlow<LinkConversionResult?>(null)
    val linkResult: StateFlow<LinkConversionResult?> = _linkResult.asStateFlow()

    val conversionLogs: StateFlow<List<LinkConversionLog>> = repository.linkConversionLogs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun updateLinkInput(text: String) {
        _linkInput.value = text
    }

    fun convertCurrentLink() {
        if (_linkInput.value.isBlank()) return
        viewModelScope.launch {
            val result = repository.processAndLogLink(_linkInput.value)
            _linkResult.value = result
        }
    }

    fun clearConversionLogs() {
        viewModelScope.launch {
            repository.clearLinkLogs()
        }
    }

    // Modal & Dialog Visibility State
    private val _showPublishGameDialog = MutableStateFlow(false)
    val showPublishGameDialog: StateFlow<Boolean> = _showPublishGameDialog.asStateFlow()

    fun openPublishGameDialog() { _showPublishGameDialog.value = true }
    fun closePublishGameDialog() { _showPublishGameDialog.value = false }

    private val _showUploadBlueprintDialog = MutableStateFlow(false)
    val showUploadBlueprintDialog: StateFlow<Boolean> = _showUploadBlueprintDialog.asStateFlow()

    fun openUploadBlueprintDialog() { _showUploadBlueprintDialog.value = true }
    fun closeUploadBlueprintDialog() { _showUploadBlueprintDialog.value = false }

    private val _selectedGameDetail = MutableStateFlow<GameItem?>(null)
    val selectedGameDetail: StateFlow<GameItem?> = _selectedGameDetail.asStateFlow()

    fun selectGameDetail(game: GameItem?) { _selectedGameDetail.value = game }

    private val _selectedBlueprintDetail = MutableStateFlow<SfsBlueprintItem?>(null)
    val selectedBlueprintDetail: StateFlow<SfsBlueprintItem?> = _selectedBlueprintDetail.asStateFlow()

    fun selectBlueprintDetail(blueprint: SfsBlueprintItem?) { _selectedBlueprintDetail.value = blueprint }

    // Toast message trigger
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    // Publishing Game Actions
    fun publishNewGame(
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
    ) {
        viewModelScope.launch {
            val created = repository.publishGame(
                title = title,
                developer = developer,
                languageEngine = languageEngine,
                description = description,
                rawDownloadUrl = rawDownloadUrl,
                webPreviewUrl = webPreviewUrl,
                category = category,
                version = version,
                tags = tags,
                sampleCodeSnippet = sampleCodeSnippet
            )
            closePublishGameDialog()
            showToast("Game '${created.title}' berhasil dipublikasikan!")
        }
    }

    fun toggleGameBookmark(game: GameItem) {
        viewModelScope.launch {
            repository.toggleBookmarkGame(game)
            showToast(if (!game.isBookmarked) "Disimpan ke Favorit" else "Dihapus dari Favorit")
        }
    }

    fun deleteGame(game: GameItem) {
        viewModelScope.launch {
            repository.deleteGame(game.id)
            if (_selectedGameDetail.value?.id == game.id) {
                _selectedGameDetail.value = null
            }
            showToast("Game telah dihapus")
        }
    }

    // Blueprint Upload Actions
    fun uploadNewSfsBlueprint(
        title: String,
        author: String,
        description: String,
        blueprintData: String,
        rocketCategory: String,
        partsCount: Int,
        massTons: Float,
        thrustTons: Float,
        targetDestination: String
    ) {
        viewModelScope.launch {
            val created = repository.uploadSfsBlueprint(
                title = title,
                author = author,
                description = description,
                blueprintData = blueprintData,
                rocketCategory = rocketCategory,
                partsCount = partsCount,
                massTons = massTons,
                thrustTons = thrustTons,
                targetDestination = targetDestination
            )
            closeUploadBlueprintDialog()
            showToast("Blueprint '${created.title}' berhasil dibagikan!")
        }
    }

    fun toggleBlueprintBookmark(bp: SfsBlueprintItem) {
        viewModelScope.launch {
            repository.toggleBookmarkBlueprint(bp)
            showToast(if (!bp.isBookmarked) "Blueprint Disimpan" else "Blueprint Dihapus dari Favorit")
        }
    }

    fun likeBlueprint(bp: SfsBlueprintItem) {
        viewModelScope.launch {
            repository.incrementBlueprintLikes(bp)
            showToast("Menyukai blueprint '${bp.title}'")
        }
    }

    fun deleteBlueprint(bp: SfsBlueprintItem) {
        viewModelScope.launch {
            repository.deleteSfsBlueprint(bp.id)
            if (_selectedBlueprintDetail.value?.id == bp.id) {
                _selectedBlueprintDetail.value = null
            }
            showToast("Blueprint telah dihapus")
        }
    }
}
