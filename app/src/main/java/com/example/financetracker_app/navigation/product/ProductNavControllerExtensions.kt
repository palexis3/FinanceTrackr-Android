package com.example.financetracker_app.navigation.product

import androidx.navigation.NavHostController
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo

fun NavHostController.navigateToProductDetails(productId: String) {
    this.navigateSingleTopTo("${ProductDetails.route}/$productId")
}

fun NavHostController.navigateToProductImage(productId: String) {
    this.navigateSingleTopTo("${ProductImage.route}/$productId")
}

fun NavHostController.navigateToProductUpdate(productId: String) {
    this.navigateSingleTopTo("${ProductUpdate.route}/$productId")
}
