package com.example.financetracker_app.navigation.receipt

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.financetracker_app.navigation.LeafScreenDestination
import com.example.financetracker_app.navigation.RootScreenDestination

object ReceiptRoot : RootScreenDestination {
    override val route = "receiptRoot"
}

object ReceiptList : LeafScreenDestination {
    override val route = "receipts"
}

object ReceiptDetails : LeafScreenDestination {
    override val route = "receipt"
    const val receiptIdArg = "receipt_id"
    val routeWithArgs = "$route/{$receiptIdArg}"
    val arguments = listOf(
        navArgument(receiptIdArg) { type = NavType.StringType }
    )
}
