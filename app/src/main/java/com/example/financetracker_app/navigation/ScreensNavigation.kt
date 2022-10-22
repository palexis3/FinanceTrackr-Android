package com.example.financetracker_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.navigation.extensions.navigateToProductDetails
import com.example.financetracker_app.navigation.extensions.navigateToProductImage
import com.example.financetracker_app.ui.composable.HomeScreen
import com.example.financetracker_app.ui.composable.product.ProductAddImageScreen
import com.example.financetracker_app.ui.composable.product.ProductCreateScreen
import com.example.financetracker_app.ui.composable.product.ProductDetailsScreen
import com.example.financetracker_app.ui.composable.product.ProductListScreen

@Composable
fun ScreensNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    showSnackbar: (String, String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(
                onClickSeeAllProducts = {
                    navHostController.navigateSingleTopTo(ProductList.route)
                },
                onClickSeeAllReceipts = {}
            )
        }

        composable(route = ProductList.route) {
            ProductListScreen(
                onClickSeeProductDetailsScreen = { id ->
                    navHostController.navigateToProductDetails(id)
                },
                onClickCreateProduct = {
                    navHostController.navigateSingleTopTo(ProductCreate.route)
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
                    id = productId,
                    goToUpdateScreen = {}
                )
            }
        }

        composable(route = ProductCreate.route) {
            ProductCreateScreen(
                closeScreen = { navHostController.popBackStack() },
                showSnackbar = showSnackbar,
                goToImageScreen = { id ->
                    navHostController.navigateToProductImage(id)
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
                    closeScreen = { navHostController.popBackStack() },
                    showSnackbar = showSnackbar,
                    productId = productId
                )
            }
        }
    }
}
