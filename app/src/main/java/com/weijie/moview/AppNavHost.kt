package com.weijie.moview

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weijie.moview.views.LoginScreen
import com.weijie.moview.views.MovieDetailScreen
import com.weijie.moview.views.MovieListScreen


@Composable
fun AppNavHost(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("movieList") {
            MovieListScreen(navController = navController)
        }
        composable("movieDetail/{id}") { backStackEntry ->
            val imdbID = backStackEntry.arguments?.getString("id")
            imdbID?.let {
                MovieDetailScreen(imdbID = it, navController = navController)
            }
        }
    }
}