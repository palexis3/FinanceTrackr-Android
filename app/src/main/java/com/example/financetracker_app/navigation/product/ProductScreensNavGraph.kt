package com.example.financetracker_app.navigation.product

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.ui.composable.product.*

fun NavGraphBuilder.productScreensNavGraph(navController: NavHostController, showSnackbar: (String, String) -> Unit) {
    navigation(startDestination = ProductList.route, route = ProductRoot.route) {
        composable(route = ProductList.route) {
            ProductListScreen(
                goToProductDetailsScreen = { id ->
                    navController.navigateToProductDetails(id)
                },
                goToProductCreateScreen = {
                    navController.navigateSingleTopTo(ProductCreate.route)
                },
                closeScreen = { navController.popBackStack() }
            )
        }

        composable(
            route = ProductDetails.routeWithArgs,
            arguments = ProductDetails.arguments
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString(ProductDetails.productIdArg)
            if (productId != null) {
                ProductDetailsScreen(
                    productId = productId,
                    goToUpdateScreen = {
                        navController.navigateToProductUpdate(productId)
                    },
                    closeScreen = { navController.popBackStack() },
                    showSnackbar = showSnackbar
                )
            }
        }

        composable(route = ProductCreate.route) {
            ProductCreateScreen(
                closeScreen = { navController.popBackStack() },
                showSnackbar = showSnackbar,
                goToImageScreen = { id ->
                    navController.navigateToProductImage(id)
                }
            )
        }

        composable(
            route = ProductImage.routeWithArgs,
            arguments = ProductImage.arguments
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString(ProductImage.productIdArg)
            if (productId != null) {
                ProductAddImageScreen(
                    closeScreen = { navController.popBackStack() },
                    showSnackbar = showSnackbar,
                    productId = productId
                )
            }
        }

        composable(
            route = ProductUpdate.routeWithArgs,
            arguments = ProductUpdate.arguments
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString(ProductUpdate.productIdArg)
            if (productId != null) {
                ProductUpdateScreen(
                    productId = productId,
                    closeScreen = { navController.popBackStack() },
                    showSnackbar = showSnackbar,
                    goToImageScreen = {
                        navController.navigateToProductImage(productId)
                    }
                )
            }
        }
    }
}
