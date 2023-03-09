package com.cyberwalker.fashionstore.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.profile.components.ImageBox
import com.cyberwalker.fashionstore.profile.components.LogoutButton
import com.cyberwalker.fashionstore.profile.components.ProfileInfo
import kotlinx.coroutines.launch

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
    onAction: (actions: ProfileScreenActions) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state = viewModel.logoutState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
            LogoutButton(viewModel = viewModel)

            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(context,"$success", Toast.LENGTH_LONG).show()
                        onAction(ProfileScreenActions.Login)
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        throw IllegalStateException("$error")
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (state.value?.isLoading==true) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

sealed class ProfileScreenActions {
    object Home : ProfileScreenActions()
    object Favorites : ProfileScreenActions()
    object Search : ProfileScreenActions()
    object Login : ProfileScreenActions()
    object Back : ProfileScreenActions()
}