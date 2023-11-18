package com.example.tmdb.feature.detail.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DetailScreen(
    navController: NavController
) {
    Log.d("TAG", "HomeScreen: ${navController.currentDestination?.route}")
}