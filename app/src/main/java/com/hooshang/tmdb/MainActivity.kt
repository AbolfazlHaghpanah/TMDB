package com.hooshang.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hooshang.tmdb.core.ui.component.TMDBBottomNavigation
import com.hooshang.tmdb.core.ui.component.TMDBSnackBar
import com.hooshang.tmdb.core.ui.theme.TMDBTheme
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.navigation.AppScreens
import com.hooshang.tmdb.navigation.mainNavGraph
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var snackBarManager: SnackBarManager

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
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
            val bottomSheetNavigatorState =
                rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Expanded,
                    skipHalfExpanded = true
                )
            val bottomSheetNavigator = remember {
                BottomSheetNavigator(bottomSheetNavigatorState)
            }
            val navController = rememberNavController(bottomSheetNavigator)
            val snackBarHostState = remember {
                SnackbarHostState()
            }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val context = LocalContext.current

            navController.addOnDestinationChangedListener(
                listener = { _, _, _ ->
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
            )

            LaunchedEffect(Unit) {
                snackBarManager.snackBarMessage.collectLatest { snackBarMessage ->
                    if (snackBarMessage?.snackBarMessage.isNullOrEmpty().not()) {
                        val snackBarResult = snackBarHostState.showSnackbar(
                            message = snackBarMessage?.snackBarMessage!!,
                            actionLabel = snackBarMessage.snackBarActionLabel?.asString(context = context),
                            duration = snackBarMessage.snackBarDuration
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            snackBarMessage.performAction()
                        }
                    }
                }
            }

            TMDBTheme {

                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetShape = TMDBTheme.shapes.veryLarge,
                    scrimColor = Color.Transparent
                ) {

                    Scaffold(
                        modifier = Modifier.imePadding(),
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            AnimatedVisibility(
                                visible = navController.currentBackStackEntryAsState()
                                    .value?.destination?.route != AppScreens.Detail.route,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                TMDBBottomNavigation(
                                    items = listOf(
                                        BottomNavigationItems.Home,
                                        BottomNavigationItems.Search,
                                        BottomNavigationItems.Favorite
                                    ),
                                    isSelected = { itemRoute ->
                                        navBackStackEntry?.destination?.hierarchy?.any { it.route == itemRoute } == true
                                    },
                                    onNavItemClick = { route ->
                                        navController.navigate(route) {
                                            popUpTo(
                                                navController
                                                    .graph
                                                    .findStartDestination().route!!
                                            ) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        },
                        snackbarHost = {
                            SnackbarHost(snackBarHostState) {
                                TMDBSnackBar(
                                    message = it.message,
                                    actionLabel = it.actionLabel,
                                    performAction = {
                                        it.performAction()
                                    }
                                )
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
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
