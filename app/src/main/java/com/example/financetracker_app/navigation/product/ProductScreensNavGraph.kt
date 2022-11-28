package com.example.financetracker_app.navigation.product

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financetracker_app.R
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.ui.composable.product.*

fun NavGraphBuilder.productScreensNavGraph(
    navController: NavHostController,
    showSnackbar: (String, String) -> Unit,
    onTitle: (String) -> Unit
) {
    navigation(startDestination = ProductList.route, route = ProductRoot.route) {
        composable(route = ProductList.route) {
            onTitle(stringResource(id = R.string.product_list))
            ProductListScreen(
                goToProductDetailsScreen = { id ->
                    navController.navigateToProductDetails(id)
                },
                goToProductCreateScreen = {
                    navController.navigateSingleTopTo(ProductCreate.route)
                }
            )
        }

        composable(
            route = ProductDetails.routeWithArgs,
            arguments = ProductDetails.arguments
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString(ProductDetails.productIdArg)
            if (productId != null) {
                onTitle(stringResource(id = R.string.product_details))
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
            onTitle(stringResource(id = R.string.product_create))
            ProductCreateScreen(
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
                onTitle(stringResource(id = R.string.product_update))
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
