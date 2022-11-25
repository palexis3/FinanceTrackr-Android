package com.example.financetracker_app.navigation

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.navigation.product.ProductRoot
import com.example.financetracker_app.navigation.receipt.ReceiptRoot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FinanceTrackrAppState(
    val navController: NavHostController,
    val snackbarScope: CoroutineScope,
    val scaffoldState: ScaffoldState
) {
    val bottomNavScreens = listOf(
        ProductRoot,
        ReceiptRoot
    )

    private val bottomNavRoutes = bottomNavScreens.map { it.route }

    val currentRoute: String?
        get() = navController.currentDestination?.route

    val isBottomNavScreen: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in bottomNavRoutes

    fun navigateToScreen(route: String) {
        if (currentRoute != route) {
            navController.navigateSingleTopTo(route)
        }
    }

    fun showSnackBar(message: String, actionLabel: String) {
        snackbarScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel
            )
        }
    }
}

@Composable
fun rememberFinanceTrackrAppState(
    navHostController: NavHostController = rememberNavController(),
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = remember {
            SnackbarHostState()
        }
    )
) = remember(navHostController, snackbarScope, scaffoldState) {
    FinanceTrackrAppState(navHostController, snackbarScope, scaffoldState)
}

@Composable
fun BottomBar(
    bottomNavScreens: List<RootScreenDestination>,
    currentRoute: String,
    navigateToScreen: (String) -> Unit
) {
    BottomAppBar {
        bottomNavScreens.forEach { screen ->
            BottomNavigationItem(
                selected = screen.route == currentRoute,
                onClick = { navigateToScreen(screen.route) },
                label = { Text(screen.title) },
                icon = {}
            )
        }
    }
}
