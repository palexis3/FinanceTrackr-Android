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

object ProductDetails : ScreenDestination {
    override val route = "product"
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.StringType }
    )
}
