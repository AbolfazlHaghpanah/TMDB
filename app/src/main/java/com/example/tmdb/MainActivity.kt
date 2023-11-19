package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.core.ui.component.TMDBBottomNavigation
import com.example.tmdb.core.ui.theme.TMDBTheme
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.navigation.AppScreens
import com.example.tmdb.navigation.mainNavGraph
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb()
            )
        )
        setContent {
            val scaffoldState = rememberScaffoldState()
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)

            TMDBTheme {

                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetShape = TMDBTheme.shapes.veryLarge,
                    scrimColor = Color.Transparent
                ) {

                    Scaffold(
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            TMDBBottomNavigation(
                                navController = navController,
                                //TODO check it when saved state handler set for detail
                                bottomBarState = navController.currentBackStackEntryAsState()
                                    .value?.destination?.route != AppScreens.Detail.route
                            )
                        }) {
                        Box(
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(it)
                                .fillMaxSize()
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = AppScreens.Home.route
                            ) {
                                mainNavGraph(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}