package com.example.studyapp.drawer

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class DrawerViewModel(val navController: NavHostController) : ViewModel() {
    private val stack = ArrayDeque<String>()

    var selectedIndex by mutableIntStateOf(0)
        private set

    init {
        stack.addLast("main")
    }

    fun navigate(route: String) {
        if (stack.last() != route) {
            navController.navigate(route) {
                popUpTo(route) { inclusive = true }
            }

            changeSelectItem(route)
            changeStack(route)
        }
    }

    fun restart() {
        val route = stack.last()

        navController.navigate(route) {
            popUpTo(route) { inclusive = true }
        }

        changeSelectItem(route)
        changeStack(route)
    }

    fun pressBack() {
        navController.popBackStack()

        if (stack.size > 1) {
            stack.removeLast()
            changeSelectItem(stack.last())
        }
    }

    private fun changeSelectItem(route: String) {
        val itemIndex = when (route) {
            "sign_in" -> 1
            "card" -> 2
            "quiz" -> 3
            "settings" -> 4
            else -> 0
        }

        selectedIndex = itemIndex
    }

    private fun changeStack(route: String) {
        if (route in stack) {
            while (stack.isNotEmpty() && stack.last() != route) {
                stack.removeLast()
            }
        } else {
            stack.addLast(route)
        }
    }
}