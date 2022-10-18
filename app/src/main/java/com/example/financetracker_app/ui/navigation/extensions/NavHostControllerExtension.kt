package com.example.financetracker_app.ui.navigation.extensions

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.financetracker_app.ui.navigation.ProductDetails

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToProductDetails(productId: String) {
    this.navigateSingleTopTo("${ProductDetails.route}/$productId")
}
