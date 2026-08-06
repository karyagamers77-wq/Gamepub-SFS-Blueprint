package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.GameDetailModal
import com.example.ui.components.PublishGameDialog
import com.example.ui.components.SfsDetailModal
import com.example.ui.components.TopHeaderBar
import com.example.ui.components.UploadBlueprintDialog
import com.example.ui.screens.DevGuidesScreen
import com.example.ui.screens.GameHubScreen
import com.example.ui.screens.LinkConverterScreen
import com.example.ui.screens.SfsBlueprintScreen
import com.example.ui.theme.GamePubTheme
import com.example.ui.theme.SunsetOrange
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamePubTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isExpandedScreen = configuration.screenWidthDp >= 600

    // ViewModel state observation
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedLanguage by viewModel.selectedLanguageFilter.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategoryFilter.collectAsStateWithLifecycle()
    val selectedSfsCategory by viewModel.selectedSfsCategory.collectAsStateWithLifecycle()

    val filteredGames by viewModel.filteredGames.collectAsStateWithLifecycle()
    val filteredSfsBlueprints by viewModel.filteredSfsBlueprints.collectAsStateWithLifecycle()

    val linkInput by viewModel.linkInput.collectAsStateWithLifecycle()
    val linkResult by viewModel.linkResult.collectAsStateWithLifecycle()
    val conversionLogs by viewModel.conversionLogs.collectAsStateWithLifecycle()

    val showPublishGameDialog by viewModel.showPublishGameDialog.collectAsStateWithLifecycle()
    val showUploadBlueprintDialog by viewModel.showUploadBlueprintDialog.collectAsStateWithLifecycle()
    val selectedGameDetail by viewModel.selectedGameDetail.collectAsStateWithLifecycle()
    val selectedBlueprintDetail by viewModel.selectedBlueprintDetail.collectAsStateWithLifecycle()

    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // Trigger toast/snackbar messages
    LaunchedEffect(toastMessage) {
        toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopHeaderBar(
                currentTab = currentTab,
                searchQuery = searchQuery,
                onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                totalGamesCount = filteredGames.size,
                totalBlueprintsCount = filteredSfsBlueprints.size
            )
        },
        bottomBar = {
            if (!isExpandedScreen) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    AppTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(tab) },
                            icon = {
                                Icon(
                                    imageVector = when (tab) {
                                        AppTab.GAMES_HUB -> Icons.Default.Gamepad
                                        AppTab.SFS_BLUEPRINTS -> Icons.Default.Rocket
                                        AppTab.LINK_CONVERTER -> Icons.Default.Link
                                        AppTab.DEV_GUIDE -> Icons.Default.Code
                                    },
                                    contentDescription = tab.title
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = if (tab == AppTab.SFS_BLUEPRINTS) SunsetOrange else MaterialTheme.colorScheme.primary,
                                selectedTextColor = if (tab == AppTab.SFS_BLUEPRINTS) SunsetOrange else MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Navigation Rail for Wide / Tablet Screens
            if (isExpandedScreen) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    AppTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationRailItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(tab) },
                            icon = {
                                Icon(
                                    imageVector = when (tab) {
                                        AppTab.GAMES_HUB -> Icons.Default.Gamepad
                                        AppTab.SFS_BLUEPRINTS -> Icons.Default.Rocket
                                        AppTab.LINK_CONVERTER -> Icons.Default.Link
                                        AppTab.DEV_GUIDE -> Icons.Default.Code
                                    },
                                    contentDescription = tab.title
                                )
                            },
                            label = { Text(tab.title, fontSize = 11.sp) },
                            colors = NavigationRailItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }

            // Screen Content Area
            Box(modifier = Modifier.weight(1f)) {
                when (currentTab) {
                    AppTab.GAMES_HUB -> {
                        GameHubScreen(
                            games = filteredGames,
                            selectedLanguage = selectedLanguage,
                            onLanguageSelect = { viewModel.updateLanguageFilter(it) },
                            selectedCategory = selectedCategory,
                            onCategorySelect = { viewModel.updateCategoryFilter(it) },
                            onGameSelect = { viewModel.selectGameDetail(it) },
                            onBookmarkToggle = { viewModel.toggleGameBookmark(it) },
                            onPublishClick = { viewModel.openPublishGameDialog() },
                            isExpandedScreen = isExpandedScreen
                        )
                    }
                    AppTab.SFS_BLUEPRINTS -> {
                        SfsBlueprintScreen(
                            blueprints = filteredSfsBlueprints,
                            selectedCategory = selectedSfsCategory,
                            onCategorySelect = { viewModel.updateSfsCategory(it) },
                            onBlueprintSelect = { viewModel.selectBlueprintDetail(it) },
                            onBookmarkToggle = { viewModel.toggleBlueprintBookmark(it) },
                            onLikeClick = { viewModel.likeBlueprint(it) },
                            onCopySuccess = { viewModel.showToast("Blueprint disalin! Tempel di SFS / Text Editor.") },
                            onUploadClick = { viewModel.openUploadBlueprintDialog() },
                            isExpandedScreen = isExpandedScreen
                        )
                    }
                    AppTab.LINK_CONVERTER -> {
                        LinkConverterScreen(
                            linkInput = linkInput,
                            onLinkInputChange = { viewModel.updateLinkInput(it) },
                            conversionResult = linkResult,
                            onConvertClick = { viewModel.convertCurrentLink() },
                            conversionLogs = conversionLogs,
                            onClearLogsClick = { viewModel.clearConversionLogs() },
                            onCopyToast = { viewModel.showToast("Tautan terkonversi disalin!") }
                        )
                    }
                    AppTab.DEV_GUIDE -> {
                        DevGuidesScreen(
                            onCopyToast = { viewModel.showToast("Kode panduan disalin!") }
                        )
                    }
                }
            }
        }

        // Modals & Dialogs
        if (showPublishGameDialog) {
            PublishGameDialog(
                onDismissRequest = { viewModel.closePublishGameDialog() },
                onPublishSubmit = { title, dev, lang, desc, url, preview, cat, ver, tags, snippet ->
                    viewModel.publishNewGame(title, dev, lang, desc, url, preview, cat, ver, tags, snippet)
                }
            )
        }

        if (showUploadBlueprintDialog) {
            UploadBlueprintDialog(
                onDismissRequest = { viewModel.closeUploadBlueprintDialog() },
                onUploadSubmit = { title, author, desc, code, cat, parts, mass, thrust, dest ->
                    viewModel.uploadNewSfsBlueprint(title, author, desc, code, cat, parts, mass, thrust, dest)
                }
            )
        }

        selectedGameDetail?.let { game ->
            GameDetailModal(
                game = game,
                onDismissRequest = { viewModel.selectGameDetail(null) },
                onDeleteGame = { viewModel.deleteGame(game) },
                onCopyCodeToast = { viewModel.showToast("Kode/Tautan disalin ke clipboard!") }
            )
        }

        selectedBlueprintDetail?.let { bp ->
            SfsDetailModal(
                blueprint = bp,
                onDismissRequest = { viewModel.selectBlueprintDetail(null) },
                onDeleteBlueprint = { viewModel.deleteBlueprint(bp) },
                onCopyBlueprintToast = { viewModel.showToast("Blueprint SFS disalin!") }
            )
        }
    }
}

