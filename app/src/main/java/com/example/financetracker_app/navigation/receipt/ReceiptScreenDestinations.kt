package com.example.financetracker_app.navigation.receipt

import com.example.financetracker_app.navigation.LeafScreenDestination
import com.example.financetracker_app.navigation.RootScreenDestination

object ReceiptRoot : RootScreenDestination {
    override val route = "receiptRoot"
}

object ReceiptList : LeafScreenDestination {
    override val route = "receipts"
}
