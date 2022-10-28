package com.example.financetracker_app.navigation.receipt

import androidx.navigation.NavHostController
import com.example.financetracker_app.navigation.extensions.navigateSingleTopTo

fun NavHostController.navigateToReceiptDetails(receiptId: String) =
    this.navigateSingleTopTo("${ReceiptDetails.route}/$receiptId")
