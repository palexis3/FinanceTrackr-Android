package com.example.financetracker_app.navigation.extensions

import androidx.navigation.NavHostController
import com.example.financetracker_app.navigation.ProductDetails
import com.example.financetracker_app.navigation.ProductImage
import com.example.financetracker_app.navigation.ProductUpdate

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToProductDetails(productId: String) {
    this.navigateSingleTopTo("${ProductDetails.route}/$productId")
}

fun NavHostController.navigateToProductImage(productId: String) {
    this.navigateSingleTopTo("${ProductImage.route}/$productId")
}

fun NavHostController.navigateToProductUpdate(productId: String) {
    this.navigateSingleTopTo("${ProductUpdate.route}/$productId")
}
