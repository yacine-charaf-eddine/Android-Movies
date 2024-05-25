package com.example.moviesapp.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.ui.moviedetailscreen.MovieDetailScreen
import com.example.moviesapp.ui.movielistscreen.MovieListScreen


@Composable
fun MinNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "movieList") {
        composable("movieList") {
            MovieListScreen(modifier = Modifier.padding(16.dp), navController = navController)
        }
        composable("movieDetail/{movieId}", arguments = listOf(navArgument("movieId") { type = NavType.StringType })) { backStackEntry ->
            backStackEntry.arguments?.getString("movieId")
                ?.let { MovieDetailScreen(movieId = it, navController = navController) }
        }

    }
}