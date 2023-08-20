package com.example.studyapp.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerItem(item: DrawerDetails, isSelected: Boolean, onClickFunc: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(10.dp),
        label =  { Text(text = item.title) },
        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
        selected = isSelected,
        onClick = { onClickFunc() }
    )
}