package com.example.financetracker_app.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.navigation.product.ProductRoot
import com.example.financetracker_app.navigation.receipt.ReceiptRoot
import com.example.financetracker_app.ui.composable.HomeScreen

fun NavGraphBuilder.homeScreensNavGraph(navController: NavHostController) {
    navigation(startDestination = Home.route, route = HomeRoot.route) {
        composable(route = Home.route) {
            HomeScreen(
                onClickSeeAllProducts = {
                    navController.navigateSingleTopTo(ProductRoot.route)
                },
                onClickSeeAllReceipts = {
                    navController.navigateSingleTopTo(ReceiptRoot.route)
                }
            )
        }
    }
}
