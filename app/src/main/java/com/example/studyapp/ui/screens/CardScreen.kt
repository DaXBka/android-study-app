package com.example.studyapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.data.base.CardDataItem
import com.example.studyapp.data.base.MainViewModel
import com.example.studyapp.drawer.DrawerViewModel
import com.example.studyapp.ui.components.BackPressHandler

@Composable
fun CardScreen(drawerModel: DrawerViewModel, viewModel: MainViewModel) {
    val frontText = remember { mutableStateOf("") }
    val backText = remember { mutableStateOf("") }

    val context = LocalContext.current
    val toastText = stringResource(R.string.cardscreen_toast_create)

    BackPressHandler { drawerModel.pressBack() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7F),
        contentAlignment = Alignment.Center
    ) {
        Form(frontText, backText) {
            val item = CardDataItem(frontText.value, backText.value)
            viewModel.insertItem(item)
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            drawerModel.navigate("main")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(frontText: MutableState<String>, backText: MutableState<String>, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 52.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.cardscreen_title),
            fontSize = 24.sp,
            fontWeight = FontWeight(700)
        )

        Spacer(modifier = Modifier.height(26.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = frontText.value,
            onValueChange = { frontText.value = it },
            label = { Text(stringResource(R.string.cardscreen_input_1)) },
            shape = MaterialTheme.shapes.medium,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Sentences)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = backText.value,
            onValueChange = { backText.value = it },
            label = { Text(stringResource(R.string.cardscreen_input_2)) },
            shape = MaterialTheme.shapes.medium,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Sentences)
        )

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClick() },
            enabled = frontText.value.isNotEmpty() && backText.value.isNotEmpty()
        ) {
            Text(stringResource(R.string.cardscreen_btn))
        }
    }
}
