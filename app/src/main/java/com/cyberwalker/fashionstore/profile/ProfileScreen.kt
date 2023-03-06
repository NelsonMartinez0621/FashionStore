package com.cyberwalker.fashionstore.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onAction: (actions: ProfileScreenActions) -> Unit){
    Surface {
        TopBar(modifier = modifier, onAction = onAction)
        ProfileScreenContent()
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, onAction: (actions: ProfileScreenActions) -> Unit){
    Surface(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onAction(ProfileScreenActions.Back) }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "close")
            }
            Text(text = "Profile")
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "close")
            }
        }
    }
}

@Composable
fun ProfileScreenContent(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Profile Screen")
        }
    }
}

sealed class ProfileScreenActions {
    object Back: ProfileScreenActions()
}