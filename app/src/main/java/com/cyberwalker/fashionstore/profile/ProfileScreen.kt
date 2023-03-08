package com.cyberwalker.fashionstore.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.profile.components.ImageBox
import com.cyberwalker.fashionstore.profile.components.LogoutButton
import com.cyberwalker.fashionstore.profile.components.ProfileInfo

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: ProfileScreenActions) -> Unit,
    navController: NavController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { paddingValues ->
        ProfileScreenContent(modifier = modifier.padding(paddingValues), onAction = onAction)
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, onAction: (actions: ProfileScreenActions) -> Unit) {
    var mDisplayMenu by remember {
        mutableStateOf(false)
    }
    Surface {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = { onAction(ProfileScreenActions.Back) }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "close")
            }
            Text(text = "Profile")
            Box {
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "close")
                }
                DropdownMenu(expanded = mDisplayMenu, onDismissRequest = { mDisplayMenu = false }) {
                    DropdownMenuItem(onClick = { onAction(ProfileScreenActions.Home) }) {
                        Text(text = "Home")
                    }
                    DropdownMenuItem(onClick = { onAction(ProfileScreenActions.Search) }) {
                        Text(text = "Search")
                    }
                    DropdownMenuItem(onClick = { onAction(ProfileScreenActions.Favorites) }) {
                        Text(text = "Liked")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    onAction: (actions: ProfileScreenActions) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        TopBar(onAction = onAction)
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageBox(onContinueClicked = { }, imageUrl = "")
            ProfileInfo(onContinueClicked = { }, info = "", label = "Name")
            ProfileInfo(onContinueClicked = { }, info = "", label = "Last Name")
            ProfileInfo(onContinueClicked = { }, info = "", label = "Email")
            LogoutButton(onContinueClicked = {})
        }
    }
}

sealed class ProfileScreenActions {
    object Home : ProfileScreenActions()
    object Favorites : ProfileScreenActions()
    object Search : ProfileScreenActions()
    object Profile : ProfileScreenActions()
    object Back : ProfileScreenActions()
}