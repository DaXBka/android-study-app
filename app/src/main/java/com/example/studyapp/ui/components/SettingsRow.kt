package com.example.studyapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun SettingsRow(title: String, selectItems: List<String>, activeItem: String, onChoice: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectValue by remember { mutableStateOf(activeItem) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Box(contentAlignment = Alignment.TopEnd) {
            Text(
                text = selectValue,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { expanded = true }
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(0.4F),
                expanded = expanded,
                onDismissRequest = { expanded = false }) {

                selectItems.forEachIndexed { idx, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            if (item != selectValue) {
                                selectValue = item
                                onChoice()
                            }

                            expanded = false
                        }
                    )

                    if (idx + 1 < selectItems.size) {
                        Divider()
                    }
                }
            }
        }
    }
}