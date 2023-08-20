package com.example.studyapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.studyapp.R
import com.example.studyapp.data.base.MainViewModel
import com.example.studyapp.drawer.DrawerViewModel
import com.example.studyapp.ui.components.BackPressHandler
import com.example.studyapp.ui.components.NoCardsScreen
import com.example.studyapp.ui.components.SwipeableCard

@OptIn(ExperimentalSwipeableCardApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun QuizScreen(drawerModel: DrawerViewModel, viewModel: MainViewModel) {
    BackPressHandler { drawerModel.pressBack() }

    val allItems by viewModel.allItems.observeAsState(listOf())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (allItems.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.quizscreen_end_title))
                Spacer(Modifier.height(10.dp))
                Button(onClick = { drawerModel.restart() }) {
                    Text(stringResource(R.string.quizscreen_end_button))
                }
            }

            allItems
                .map { it to rememberSwipeableCardState() }
                .forEach { (item, state) ->
                    if (state.swipedDirection == null) {
                        SwipeableCard(
                            item = item,
                            modifier = Modifier
                                .swipableCard(
                                    state = state,
                                    blockedDirections = listOf(Direction.Down, Direction.Up),
                                    onSwiped = {

                                    }
                                )
                        )
                    }
                }
        } else {
            NoCardsScreen("☹️")
        }
    }
}