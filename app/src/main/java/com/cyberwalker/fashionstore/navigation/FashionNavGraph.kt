/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.cyberwalker.fashionstore.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.detail.DetailScreen
import com.cyberwalker.fashionstore.detail.DetailScreenActions
import com.cyberwalker.fashionstore.dump.animatedComposable
import com.cyberwalker.fashionstore.favorites.FavoritesScreen
import com.cyberwalker.fashionstore.favorites.FavoritesScreenActions
import com.cyberwalker.fashionstore.home.HomeScreen
import com.cyberwalker.fashionstore.home.HomeScreenActions
import com.cyberwalker.fashionstore.login.LoginScreen
import com.cyberwalker.fashionstore.login.LoginScreenActions
import com.cyberwalker.fashionstore.profile.ProfileScreen
import com.cyberwalker.fashionstore.profile.ProfileScreenActions
import com.cyberwalker.fashionstore.search.SearchScreen
import com.cyberwalker.fashionstore.search.SearchScreenActions
import com.cyberwalker.fashionstore.signup.SignUpScreen
import com.cyberwalker.fashionstore.signup.SignUpScreenActions
import com.cyberwalker.fashionstore.splash.SplashScreen
import com.cyberwalker.fashionstore.splash.SplashScreenActions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

sealed class Screen(val name: String, val route: String) {
    object Splash : Screen("splash", "splash")
    object Home : Screen("home", "home")
    object Detail : Screen("detail", "detail")
    object SignUp: Screen("signup", "signup")
    object Login: Screen("login", "login")
    object Favorites: Screen("favorites","favorites")
    object Search: Screen("search","search")
    object Profile: Screen("profile", "profile")

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FashionNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    actions: NavActions = remember(navController) {
        NavActions(navController)
    }
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        animatedComposable(Screen.Splash.route) {
            SplashScreen(onAction = actions::navigateFromSplash)
        }

        animatedComposable(Screen.Home.route) {
            HomeScreen(onAction = actions::navigateFromHome, navController = navController)
        }

        animatedComposable(Screen.Detail.route) {
            DetailScreen(onAction = actions::navigateFromDetails)
        }

        animatedComposable(route = Screen.SignUp.route){
            SignUpScreen(onAction = actions::navigateFromSignUp, navController = navController)
        }

        animatedComposable(Screen.Login.route) {
            LoginScreen(onAction = actions::navigateFromLogin, navController = navController)
        }

        animatedComposable(Screen.Favorites.route) {
            FavoritesScreen(onAction = actions::navigateFromFavorites, navController = navController)
        }

        animatedComposable(Screen.Search.route) {
            SearchScreen(onAction = actions::navigateFromSearch, navController = navController)
        }

        animatedComposable(Screen.Profile.route) {
            ProfileScreen(onAction = actions::navigateFromProfile, navController = navController)
        }


    }
}

class NavActions(private val navController: NavController) {
    fun navigateFromSplash(actions: SplashScreenActions) {
        when (actions) {
            SplashScreenActions.LoadLogin -> {
                navController.navigate(Screen.Login.name) {
                    popUpTo(Screen.Splash.route){
                        inclusive = true
                    }
                }
            }

            SplashScreenActions.Home -> {
                navController.navigate(Screen.Home.name) {
                    popUpTo(Screen.Splash.route){
                        inclusive = true
                    }
                }
            }
        }
    }

    fun navigateFromLogin(actions: LoginScreenActions) {
        when (actions) {
            LoginScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
            LoginScreenActions.SignUp -> {
                navController.navigate(Screen.SignUp.name)
            }
        }
    }

    fun navigateFromSignUp(actions: SignUpScreenActions) {
        when (actions) {
            SignUpScreenActions.Home -> {
                navController.navigate(Screen.Home.route)
            }
            SignUpScreenActions.Login -> {
                navController.navigate(Screen.Login.name)
            }
        }
    }

    fun navigateFromHome(actions: HomeScreenActions) {
        when (actions) {
            HomeScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
            HomeScreenActions.Details -> {
                navController.navigate(Screen.Detail.name)
            }

            HomeScreenActions.Favorites -> {
                navController.navigate(Screen.Favorites.name)
            }

            HomeScreenActions.Profile -> {
                navController.navigate(Screen.Profile.name)
            }

            HomeScreenActions.Search -> {
                navController.navigate(Screen.Search.name)
            }
        }
    }

    fun navigateFromFavorites(actions: FavoritesScreenActions) {
        when(actions) {
            FavoritesScreenActions.Back -> navController.popBackStack()

            FavoritesScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
            FavoritesScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
        }
    }

    fun navigateFromDetails(actions: DetailScreenActions) {
        when(actions) {
            DetailScreenActions.Back -> navController.popBackStack()
            DetailScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
        }
    }
    fun navigateFromSearch(actions: SearchScreenActions) {
        when(actions) {
            SearchScreenActions.Back -> navController.popBackStack()

            SearchScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
            SearchScreenActions.Favorites -> {
                navController.navigate(Screen.Favorites.name)
            }
            SearchScreenActions.Profile -> {
                navController.navigate(Screen.Profile.name)
            }
        }
    }
    fun navigateFromProfile(actions: ProfileScreenActions) {
        when(actions) {
            ProfileScreenActions.Back -> navController.popBackStack()

            ProfileScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
            ProfileScreenActions.Search -> {
                navController.navigate(Screen.Search.name)
            }
            ProfileScreenActions.Favorites -> {
                navController.navigate(Screen.Favorites.name)
            }
            ProfileScreenActions.Login -> {
                navController.navigate(Screen.Login.name)
            }
        }
    }
}