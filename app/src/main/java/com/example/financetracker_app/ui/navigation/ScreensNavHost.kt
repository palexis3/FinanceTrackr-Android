package com.example.financetracker_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financetracker_app.ui.composable.HomeScreen
import com.example.financetracker_app.ui.composable.ProductListScreen

@Composable
fun ScreenNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(
                onClickSeeAllProducts = {
                    navController.navigateSingleTopTo(ProductList.route)
                },
                onClickSeeAllReceipts = {}
            )
        }

        composable(route = ProductList.route) {
            ProductListScreen()
        }
    }
}
