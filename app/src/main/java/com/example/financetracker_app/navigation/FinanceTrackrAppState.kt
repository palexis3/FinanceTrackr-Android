package com.example.financetracker_app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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

    fun closeScreen() = navController.popBackStack()

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    isBottomNavScreen: Boolean,
    closeScreen: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold
            )
        },
        navigationIcon = {
            if (isBottomNavScreen.not()) {
                IconButton(onClick = closeScreen) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            }
        }
    )
}
