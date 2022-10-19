package com.example.financetracker_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.navigation.extensions.navigateToProductDetails
import com.example.financetracker_app.ui.composable.HomeScreen
import com.example.financetracker_app.ui.composable.product.ProductCreateScreen
import com.example.financetracker_app.ui.composable.product.ProductDetailsScreen
import com.example.financetracker_app.ui.composable.product.ProductListScreen

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
            ProductListScreen(
                onClickSeeProductDetailsScreen = { id ->
                    navController.navigateToProductDetails(id)
                },
                onClickCreateProduct = {
                    navController.navigate(ProductCreate.route)
                }
            )
        }

        composable(
            route = ProductDetails.routeWithArgs,
            arguments = ProductDetails.arguments
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString(ProductDetails.productIdArg)
            if (productId != null) {
                ProductDetailsScreen(id = productId)
            }
        }

        composable(route = ProductCreate.route) {
            ProductCreateScreen(closeScreen = { navController.popBackStack() })
        }
    }
}
