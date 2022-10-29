package com.example.financetracker_app.navigation.receipt

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo
import com.example.financetracker_app.ui.composable.receipt.ReceiptCreateScreen
import com.example.financetracker_app.ui.composable.receipt.ReceiptDetailsScreen
import com.example.financetracker_app.ui.composable.receipt.ReceiptListScreen

fun NavGraphBuilder.receiptScreensNavGraph(navController: NavHostController, showSnackbar: (String, String) -> Unit) {
    navigation(startDestination = ReceiptList.route, route = ReceiptRoot.route) {
        composable(route = ReceiptList.route) {
            ReceiptListScreen(
                goToReceiptDetailsScreen = { receiptId ->
                    navController.navigateToReceiptDetails(receiptId)
                },
                goToReceiptCreateScreen = {
                    navController.navigateSingleTopTo(ReceiptCreate.route)
                }
            )
        }

        composable(
            route = ReceiptDetails.routeWithArgs,
            arguments = ReceiptDetails.arguments
        ) { navBackStackEntry ->
            val receiptId = navBackStackEntry.arguments?.getString(ReceiptDetails.receiptIdArg)
            if (receiptId != null) {
                ReceiptDetailsScreen(
                    receiptId = receiptId,
                    closeScreen = { navController.popBackStack() }
                )
            }
        }

        composable(route = ReceiptCreate.route) {
            ReceiptCreateScreen(
                closeScreen = { navController.popBackStack() },
                showSnackbar = showSnackbar
            )
        }
    }
}
