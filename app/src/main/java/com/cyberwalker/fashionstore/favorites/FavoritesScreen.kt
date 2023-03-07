package com.cyberwalker.fashionstore.favorites

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.dump.BottomNav


@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions:FavoritesScreenActions) -> Unit,
    navController: NavController){
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { paddingValues ->
        FavoritesScreenContent(modifier = modifier.padding(paddingValues), onAction = onAction )
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, onAction: (actions:FavoritesScreenActions) -> Unit){
    Surface(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onAction(FavoritesScreenActions.Home) }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "close")
            }
            Text(text = "Favorites")
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "close")
            }
        }
    }
}

@Composable
fun FavoritesScreenContent(
    modifier: Modifier,
    onAction: (actions: FavoritesScreenActions) -> Unit){
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TopBar(modifier, onAction = onAction)
        FavoritesFashions(modifier)
    }
}

@Composable
fun FavoritesFashions(
    modifier: Modifier = Modifier,
    names: List<String> = List(10) {"$it"}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            FavoriteFashionCard(
                drawable = R.drawable.img_1,
                text = "Style $name",
            )
        }
    }
}

@Composable
fun FavoriteFashionCard(
    @DrawableRes drawable: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(200.dp)
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(drawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = text,
                    style = typography.subtitle2,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Button(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(96.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onSecondary)
                ,
                onClick = { /*TODO*/ })
            {
                Text(
                    text = "Add to Cart",
                    color = Color.White,
                    style = typography.button
                )
            }
        }
    }
}

sealed class FavoritesScreenActions {
    object Home : FavoritesScreenActions()
    object Back : FavoritesScreenActions()
}