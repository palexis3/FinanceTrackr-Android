package com.example.financetracker_app.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.navigation.product.ProductList
import com.example.financetracker_app.navigation.receipt.ReceiptList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FinanceTrackrAppState(
    val navController: NavHostController,
    private val snackbarScope: CoroutineScope,
    private val snackbarHostState: SnackbarHostState
) {
    val bottomNavScreens = listOf(
        ProductList,
        ReceiptList
    )

    private val bottomNavRoutes = bottomNavScreens.map { it.route }

    val currentRoute: String?
        get() = navController.currentDestination?.route

    val isBottomNavScreen: Boolean
        @Composable get() =
            navController.currentBackStackEntryAsState().value?.destination?.route in bottomNavRoutes

    fun navigateToScreen(route: String) {
        if (currentRoute != route) {
            navController.navigateSingleTopTo(route)
        }
    }

    fun showSnackBar(message: String, actionLabel: String) {
        snackbarScope.launch {
            snackbarHostState.showSnackbar(
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
    snackbarHostState: SnackbarHostState = SnackbarHostState()

) = remember(navHostController, snackbarScope, snackbarHostState) {
    FinanceTrackrAppState(navHostController, snackbarScope, snackbarHostState)
}

@Composable
fun BottomBar(
    bottomNavScreens: List<LeafScreenDestination>,
    currentRoute: String,
    navigateToScreen: (String) -> Unit
) {
    NavigationBar {
        bottomNavScreens.forEach { screen ->
            NavigationBarItem(
                selected = screen.route == currentRoute,
                onClick = { navigateToScreen(screen.route) },
                label = { Text(screen.title) },
                icon = {}
            )
        }
    }
}
