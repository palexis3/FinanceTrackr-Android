package com.example.financetracker_app.navigation.product

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.financetracker_app.navigation.LeafScreenDestination
import com.example.financetracker_app.navigation.RootScreenDestination

object ProductRoot : RootScreenDestination {
    override val route = "productRoot"
    override val title = "Product"
}

object ProductList : LeafScreenDestination {
    override val route = "products"
    override val title = "Products"
}

object ProductDetails : LeafScreenDestination {
    override val route = "product"
    override val title = "Product Details"
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.StringType }
    )
}

object ProductCreate : LeafScreenDestination {
    override val route = "productCreate"
    override val title = "Product Create"
}

object ProductImage : LeafScreenDestination {
    override val route = "productImage"
    override val title = "Product Image"
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.StringType }
    )
}

object ProductUpdate : LeafScreenDestination {
    override val route = "productUpdate"
    override val title = "Product Update"
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.StringType }
    )
}
