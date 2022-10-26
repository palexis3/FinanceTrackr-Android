package com.example.financetracker_app.navigation.receipt

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financetracker_app.ui.composable.receipt.ReceiptListScreen

fun NavGraphBuilder.receiptScreensNavGraph(navController: NavController) {
    navigation(startDestination = ReceiptList.route, route = "receipt") {
        composable(route = ReceiptList.route) {
            ReceiptListScreen(goToReceiptDetailsScreen = {})
        }
    }
}
