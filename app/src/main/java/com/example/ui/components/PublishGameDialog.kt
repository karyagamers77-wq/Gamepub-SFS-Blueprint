package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.util.LinkConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishGameDialog(
    onDismissRequest: () -> Unit,
    onPublishSubmit: (
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
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var developer by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rawDownloadUrl by remember { mutableStateOf("") }
    var webPreviewUrl by remember { mutableStateOf("") }
    var version by remember { mutableStateOf("1.0.0") }
    var tags by remember { mutableStateOf("Action, Indie") }
    var sampleCodeSnippet by remember { mutableStateOf("") }

    val languagesList = listOf(
        "Unity (C#)",
        "Godot (GDScript)",
        "HTML5 / JS",
        "Kotlin / Android Java",
        "Python (Pygame)",
        "Construct 3",
        "Unreal Engine (C++)",
        "Lainnya"
    )
    var selectedLanguage by remember { mutableStateOf(languagesList[0]) }
    var languageExpanded by remember { mutableStateOf(false) }

    val categoriesList = listOf("Game", "APK Tool", "Web Game")
    var selectedCategory by remember { mutableStateOf(categoriesList[0]) }
    var categoryExpanded by remember { mutableStateOf(false) }

    // Live link preview conversion
    val convertedResult = remember(rawDownloadUrl) {
        if (rawDownloadUrl.isNotBlank()) LinkConverter.convertLink(rawDownloadUrl) else null
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .heightIn(max = 680.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Gamepad,
                            contentDescription = "Publikasi Game",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Publikasikan Game / APK",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.testTag("close_publish_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Game Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Game / APK *") },
                    placeholder = { Text("Contoh: Space Defense 3D") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_game_title")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Developer Name
                OutlinedTextField(
                    value = developer,
                    onValueChange = { developer = it },
                    label = { Text("Nama Pembuat / Pengembang *") },
                    placeholder = { Text("Contoh: Developer Nusantara") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_game_developer")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Language / Engine Dropdown
                ExposedDropdownMenuBox(
                    expanded = languageExpanded,
                    onExpandedChange = { languageExpanded = !languageExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedLanguage,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Bahasa Pemrograman / Engine *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = languageExpanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = languageExpanded,
                        onDismissRequest = { languageExpanded = false }
                    ) {
                        languagesList.forEach { lang ->
                            DropdownMenuItem(
                                text = { Text(lang) },
                                onClick = {
                                    selectedLanguage = lang
                                    languageExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Raw Download URL (supports HTML links or raw URLs)
                OutlinedTextField(
                    value = rawDownloadUrl,
                    onValueChange = { rawDownloadUrl = it },
                    label = { Text("Tautan Unduh / HTML Link *") },
                    placeholder = { Text("Contoh: <a href=\"http://game.com/app.apk\">Click</a> atau http://...") },
                    leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_game_download_url")
                )

                // Live converted URL feedback
                if (convertedResult != null && convertedResult.convertedUrl.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "URL Terkonversi (${convertedResult.protocol}): ${convertedResult.convertedUrl}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Category & Version Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Category Dropdown
                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = { categoryExpanded = !categoryExpanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Kategori") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false }
                        ) {
                            categoriesList.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        selectedCategory = cat
                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Version Field
                    OutlinedTextField(
                        value = version,
                        onValueChange = { version = it },
                        label = { Text("Versi") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Deskripsi Game *") },
                    placeholder = { Text("Ceritakan fitur dan keunggulan game buatanmu...") },
                    minLines = 3,
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Optional Web Preview URL
                OutlinedTextField(
                    value = webPreviewUrl,
                    onValueChange = { webPreviewUrl = it },
                    label = { Text("Tautan Web Preview (Opsional)") },
                    placeholder = { Text("https://mygame.com/preview") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Tags (Comma separated)
                OutlinedTextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("Tag (Pisahkan dengan koma)") },
                    placeholder = { Text("Action, Pixel, Multi, Online") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Sample Code Snippet
                OutlinedTextField(
                    value = sampleCodeSnippet,
                    onValueChange = { sampleCodeSnippet = it },
                    label = { Text("Cuplikan Kode / Script (Opsional)") },
                    placeholder = { Text("Contoh script C#, GDScript, JavaScript, atau Python...") },
                    leadingIcon = { Icon(Icons.Default.Code, contentDescription = null) },
                    minLines = 2,
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Batal")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (title.isNotBlank() && developer.isNotBlank() && rawDownloadUrl.isNotBlank() && description.isNotBlank()) {
                                onPublishSubmit(
                                    title,
                                    developer,
                                    selectedLanguage,
                                    description,
                                    rawDownloadUrl,
                                    webPreviewUrl,
                                    selectedCategory,
                                    version,
                                    tags,
                                    sampleCodeSnippet
                                )
                            }
                        },
                        enabled = title.isNotBlank() && developer.isNotBlank() && rawDownloadUrl.isNotBlank() && description.isNotBlank(),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("submit_publish_game_button")
                    ) {
                        Text("Publikasikan Game")
                    }
                }
            }
        }
    }
}
