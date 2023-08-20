package com.example.studyapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studyapp.data.base.CardDataItem
import com.example.studyapp.data.base.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlipCard(viewModel: MainViewModel, item: CardDataItem) {
    var rotated by remember { mutableStateOf(false) }
    var isDelete by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateColor by animateColorAsState(
        targetValue = if (rotated) MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(500)
    )

    val animateDeleteBtn by animateFloatAsState(
        targetValue = if (isDelete) 1f else 0f,
        animationSpec = tween(250)
    )

    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(10.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .combinedClickable(
                onClick = {
                    if (isDelete) {
                        isDelete = false
                    } else {
                        rotated = !rotated
                    }
                },
                onLongClick = {
                    isDelete = !isDelete
                }
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = animateColor
        )

    ) {
        Icon(
            modifier = Modifier
                .width((animateDeleteBtn * 32).dp)
                .height((animateDeleteBtn * 32).dp)
                .offset(x = 4.dp, y = 4.dp)
                .clickable {
                    isDelete = false
                    if (rotated) rotated = false
                    viewModel.deleteItem(item)
                },
            imageVector = Icons.Rounded.Close,
            contentDescription = "Close button"
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), contentAlignment = Alignment.Center) {
            Text(text = if (rotated) item.back else item.front,
                modifier = Modifier.graphicsLayer {
                    alpha = if (rotated) animateBack else animateFront
                    rotationY = rotation
                },
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center
            )
        }
    }
}