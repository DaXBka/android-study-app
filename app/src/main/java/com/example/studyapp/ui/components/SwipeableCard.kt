package com.example.studyapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.data.base.CardDataItem
import com.example.studyapp.ui.theme.CorrectColor
import com.example.studyapp.ui.theme.IncorrectColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableCard(item: CardDataItem, modifier: Modifier) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val primaryColor = MaterialTheme.colorScheme.primary

    var answer by remember { mutableStateOf("") }
    var inputColor by remember { mutableStateOf(primaryColor) }

    Card(
        modifier
            .fillMaxWidth(0.8f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = item.back,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(Modifier.height(10.dp))
                Text(stringResource(R.string.quizscreen_card_title))
                OutlinedTextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text(stringResource(R.string.quizscreen_card_label)) },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Sentences),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = inputColor,
                        unfocusedLabelColor = inputColor
                    )
                )
                val correctText = stringResource(R.string.quizscreen_toast_correct)
                val incorrectText = stringResource(R.string.quizscreen_toast_incorrect)

                Spacer(Modifier.height(12.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        focusManager.clearFocus()

                        if (answer.lowercase().trim() == item.front.lowercase()) {
                            inputColor = CorrectColor
                            Toast.makeText(
                                context,
                                correctText,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            inputColor = IncorrectColor
                            Toast.makeText(
                                context,
                                incorrectText,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Text(stringResource(R.string.quizscreen_card_button))
                }
            }
        }
    }
}