package com.example.financetracker_app.navigation.receipt

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financetracker_app.navigation.product.ProductDetails
import com.example.financetracker_app.ui.composable.receipt.ReceiptDetailsScreen
import com.example.financetracker_app.ui.composable.receipt.ReceiptListScreen

fun NavGraphBuilder.receiptScreensNavGraph(navController: NavHostController) {
    navigation(startDestination = ReceiptList.route, route = ReceiptRoot.route) {
        composable(route = ReceiptList.route) {
            ReceiptListScreen(goToReceiptDetailsScreen = { receiptId ->
                navController.navigateToReceiptDetails(receiptId)
            })
        }

        composable(
            route = ReceiptDetails.routeWithArgs,
            arguments = ReceiptDetails.arguments
        ) { navBackStackEntry ->
            val receiptId = navBackStackEntry.arguments?.getString(ProductDetails.productIdArg)
            if (receiptId != null) {
                ReceiptDetailsScreen(
                    receiptId = receiptId,
                    closeScreen = { navController.popBackStack() }
                )
            }
        }
    }
}
