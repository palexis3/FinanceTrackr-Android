package com.example.financetracker_app.navigation.product

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.financetracker_app.navigation.LeafScreenDestination
import com.example.financetracker_app.navigation.RootScreenDestination

object ProductRoot : RootScreenDestination {
    override val route = "productRoot"
}

object ProductList : LeafScreenDestination {
    override val route = "products"
}

object ProductDetails : LeafScreenDestination {
    override val route = "product"
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.StringType }
    )
}

object ProductCreate : LeafScreenDestination {
    override val route = "productCreate"
}

object ProductImage : LeafScreenDestination {
    override val route = "productImage"
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.StringType }
    )
}

object ProductUpdate : LeafScreenDestination {
    override val route = "productUpdate"
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.StringType }
    )
}
