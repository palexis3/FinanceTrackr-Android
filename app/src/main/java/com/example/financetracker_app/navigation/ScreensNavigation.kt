package com.example.financetracker_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.financetracker_app.navigation.product.ProductRoot
import com.example.financetracker_app.navigation.product.productScreensNavGraph
import com.example.financetracker_app.navigation.receipt.receiptScreensNavGraph

@Composable
fun ScreensNavigation(
    navController: NavHostController,
    modifier: Modifier,
    showSnackbar: (String, String) -> Unit
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = ProductRoot.route
    ) {
        productScreensNavGraph(navController, showSnackbar)
        receiptScreensNavGraph(navController, showSnackbar)
    }
}
