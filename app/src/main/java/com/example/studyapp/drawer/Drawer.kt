package com.example.studyapp.drawer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studyapp.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(viewModel: DrawerViewModel, content: @Composable () -> Unit) {
    val drawerItems = listOf(
        DrawerDetails(Icons.Filled.Home, stringResource(id = R.string.drawer_home), "main"),
        DrawerDetails(Icons.Filled.Person, "Account", "sign_in"),
        DrawerDetails(Icons.Filled.Add, stringResource(id = R.string.drawer_card), "card"),
        DrawerDetails(Icons.Filled.Info, stringResource(id = R.string.drawer_quiz), "quiz"),
        DrawerDetails(
            Icons.Filled.Settings,
            stringResource(id = R.string.drawer_settings),
            "settings"
        ),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(14.dp))

                drawerItems.forEach {
                    DrawerItem(item = it, it == drawerItems[viewModel.selectedIndex]) {
                        coroutineScope.launch {
                            viewModel.navigate(it.route)
                            drawerState.close()
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBar() {
                    coroutineScope.launch { drawerState.open() }
                }
            },
            bottomBar = {
                BottomBar() {
                    coroutineScope.launch { viewModel.navigate("card") }
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) { content() }
        }
    }
}