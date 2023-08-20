package com.example.studyapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R

@Composable
fun NoCardsScreen(emoji: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = emoji, fontSize = 72.sp)
        Spacer(Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.mainscreen_nocards_title),
            fontSize = 24.sp,
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.mainscreen_nocards_text),
            textAlign = TextAlign.Center
        )
    }
}