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
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.ui.theme.GoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadBlueprintDialog(
    onDismissRequest: () -> Unit,
    onUploadSubmit: (
        title: String,
        author: String,
        description: String,
        blueprintData: String,
        rocketCategory: String,
        partsCount: Int,
        massTons: Float,
        thrustTons: Float,
        targetDestination: String
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var blueprintData by remember { mutableStateOf("") }
    var partsCountStr by remember { mutableStateOf("45") }
    var massTonsStr by remember { mutableStateOf("120") }
    var thrustTonsStr by remember { mutableStateOf("250") }
    var targetDestination by remember { mutableStateOf("Moon (Orbit/Landing)") }

    val categories = listOf("Rockets", "Space Stations", "Landers & Rovers", "Satellites", "Experimental")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var categoryExpanded by remember { mutableStateOf(false) }

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
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Rocket,
                            contentDescription = "Bagikan Blueprint SFS",
                            tint = GoldSecondary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Bagikan Blueprint SFS Baru",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.testTag("close_sfs_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Nama Roket / Blueprint *") },
                    placeholder = { Text("Contoh: Falcon Heavy Reusable V2") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_sfs_title")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Author
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Nama Pembuat / Commander *") },
                    placeholder = { Text("Contoh: AstroBuilder99") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_sfs_author")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category & Target Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = { categoryExpanded = !categoryExpanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Kategori SFS") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false }
                        ) {
                            categories.forEach { cat ->
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

                    OutlinedTextField(
                        value = targetDestination,
                        onValueChange = { targetDestination = it },
                        label = { Text("Tujuan Misi") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Specs: Parts, Mass, Thrust Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = partsCountStr,
                        onValueChange = { partsCountStr = it },
                        label = { Text("Part (Jumlah)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = massTonsStr,
                        onValueChange = { massTonsStr = it },
                        label = { Text("Massa (Ton)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = thrustTonsStr,
                        onValueChange = { thrustTonsStr = it },
                        label = { Text("Thrust (Ton)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Deskripsi Blueprint *") },
                    placeholder = { Text("Jelaskan tahap peluncuran, stage roket, dan panduan kemudi...") },
                    minLines = 2,
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Blueprint JSON / Text Code
                OutlinedTextField(
                    value = blueprintData,
                    onValueChange = { blueprintData = it },
                    label = { Text("Data / Teks Blueprint SFS (.txt / JSON) *") },
                    placeholder = { Text("Tempelkan kode JSON / blueprint roket Space Flight Simulator di sini...") },
                    leadingIcon = { Icon(Icons.Default.Code, contentDescription = null) },
                    minLines = 4,
                    maxLines = 7,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_sfs_blueprint_code")
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Row
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
                            if (title.isNotBlank() && author.isNotBlank() && blueprintData.isNotBlank()) {
                                onUploadSubmit(
                                    title,
                                    author,
                                    description.ifBlank { "Blueprint roket Space Flight Simulator." },
                                    blueprintData,
                                    selectedCategory,
                                    partsCountStr.toIntOrNull() ?: 30,
                                    massTonsStr.toFloatOrNull() ?: 100.0f,
                                    thrustTonsStr.toFloatOrNull() ?: 200.0f,
                                    targetDestination
                                )
                            }
                        },
                        enabled = title.isNotBlank() && author.isNotBlank() && blueprintData.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldSecondary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("submit_upload_sfs_button")
                    ) {
                        Text("Bagikan Blueprint", color = androidx.compose.ui.graphics.Color.Black)
                    }
                }
            }
        }
    }
}
