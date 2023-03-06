package com.cyberwalker.fashionstore.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close


@Composable
fun SearchScreen(modifier: Modifier = Modifier, onAction: (actions: SearchScreenActions) -> Unit){
    Surface {
        TopBar(modifier = modifier, onAction = onAction)
        SearchScreenContent()
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, onAction: (actions: SearchScreenActions) -> Unit){
    Surface(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onAction(SearchScreenActions.Back) }) {
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
fun SearchScreenContent(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Search Screen")
        }
    }
}

sealed class SearchScreenActions {
    object Back: SearchScreenActions()
}