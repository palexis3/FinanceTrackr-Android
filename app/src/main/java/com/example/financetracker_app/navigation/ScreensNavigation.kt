package com.example.financetracker_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.navigation.extensions.navigateToProductDetails
import com.example.financetracker_app.navigation.extensions.navigateToProductImage
import com.example.financetracker_app.navigation.extensions.navigateToProductUpdate
import com.example.financetracker_app.navigation.receipt.ReceiptRoot
import com.example.financetracker_app.navigation.receipt.receiptScreensNavGraph
import com.example.financetracker_app.ui.composable.HomeScreen
import com.example.financetracker_app.ui.composable.product.*

@Composable
fun ScreensNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    showSnackbar: (String, String) -> Unit
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
                onClickSeeAllReceipts = {
                    navController.navigateSingleTopTo(ReceiptRoot.route)
                }
            )
        }

        composable(route = ProductList.route) {
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

        receiptScreensNavGraph(navController)
    }
}
