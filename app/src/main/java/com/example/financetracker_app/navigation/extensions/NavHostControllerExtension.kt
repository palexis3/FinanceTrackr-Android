package com.example.financetracker_app.navigation.extensions

import androidx.navigation.NavHostController
import com.example.financetracker_app.navigation.ProductDetails

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToProductDetails(productId: String) {
    this.navigateSingleTopTo("${ProductDetails.route}/$productId")
}
