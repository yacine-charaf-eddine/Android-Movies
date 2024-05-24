package com.example.moviesapp.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.ui.moviesscreen.MoviesScreen


@Composable
fun MinNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MoviesScreen(modifier = Modifier.padding(16.dp))
        }

    }
}