package com.example.studyapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.ui.components.FlipCard
import com.example.studyapp.R
import com.example.studyapp.data.base.CardDataItem
import com.example.studyapp.data.base.MainViewModel
import com.example.studyapp.drawer.DrawerViewModel
import com.example.studyapp.ui.components.BackPressHandler
import com.example.studyapp.ui.components.NoCardsScreen

@Composable
fun MainScreen(drawerModel: DrawerViewModel, viewModel: MainViewModel) {
    BackPressHandler { drawerModel.pressBack() }

    val allItems by viewModel.allItems.observeAsState(listOf())

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            text = stringResource(R.string.mainscreen_title),
            fontSize = 28.sp,
            fontWeight = FontWeight(700)
        )

        Divider(
            modifier = Modifier.padding(horizontal = 20.dp),
            thickness = 2.dp
        )

        if (allItems.isNotEmpty())
            CardListScreen(viewModel, allItems)
        else {
            NoCardsScreen("🤫")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardListScreen(viewModel: MainViewModel, items: List<CardDataItem>) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            items(items) { gridItem ->
                FlipCard(viewModel, gridItem)
            }
        }
    )
}
