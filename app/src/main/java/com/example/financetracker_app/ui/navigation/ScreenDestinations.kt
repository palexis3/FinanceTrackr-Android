package com.example.financetracker_app.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface ScreenDestination {
    val route: String
}

object Home : ScreenDestination {
    override val route = "home"
}

object ProductList : ScreenDestination {
    override val route = "products"
}

object Product : ScreenDestination {
    override val route = "product"
    const val productNameArg = "product_name"
    val routeWithArgs = "$route/{$productNameArg}"
    val arguments = listOf(
        navArgument(productNameArg) { type = NavType.StringType }
    )
}
