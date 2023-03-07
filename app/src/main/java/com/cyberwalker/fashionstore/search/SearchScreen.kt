package com.cyberwalker.fashionstore.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.profile.ProfileScreenActions

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: SearchScreenActions) -> Unit,
    navController: NavController
){
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { paddingValues ->
        SearchScreenContent(modifier = modifier.padding(paddingValues), onAction = onAction )
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, onAction: (actions: SearchScreenActions) -> Unit){
    var mDisplayMenu by remember {
        mutableStateOf(false)
    }
    Surface(modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = { onAction(SearchScreenActions.Back) }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "close")
            }
            Text(text = "Liked")
            Box {
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "close")
                }
                DropdownMenu(expanded = mDisplayMenu, onDismissRequest = { mDisplayMenu = false }) {
                    DropdownMenuItem(onClick = { onAction(SearchScreenActions.Home) }) {
                        Text(text = "Home")
                    }
                    DropdownMenuItem(onClick = { onAction(SearchScreenActions.Favorites) }) {
                        Text(text = "Liked")
                    }
                    DropdownMenuItem(onClick = { onAction(SearchScreenActions.Profile) }) {
                        Text(text = "Profile")
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreenContent(modifier: Modifier = Modifier, onAction: (actions: SearchScreenActions) -> Unit) {
    Surface(modifier = modifier.fillMaxSize()) {
        TopBar(onAction = onAction)
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Search Screen")
        }
    }
}

sealed class SearchScreenActions {
    object Home: SearchScreenActions()
    object Favorites: SearchScreenActions()
    object Search: SearchScreenActions()
    object Profile: SearchScreenActions()
    object Back: SearchScreenActions()
}