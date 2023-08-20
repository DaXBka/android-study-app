package com.example.studyapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.studyapp.R
import com.example.studyapp.drawer.DrawerViewModel
import com.example.studyapp.ui.components.BackPressHandler
import com.example.studyapp.ui.components.SettingsRow
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(drawerModel: DrawerViewModel, isDarkTheme: MutableState<Boolean>) {
    BackPressHandler { drawerModel.pressBack() }

    val themes = listOf("Light", "Dark")
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SettingsRow(
            title = stringResource(R.string.settingsscreen_theme),
            selectItems = themes,
            activeItem = if (isDarkTheme.value) themes[1] else themes[0]
        ) {
            isDarkTheme.value = !isDarkTheme.value
            scope.launch { drawerModel.navigate("main") }
        }
    }
}