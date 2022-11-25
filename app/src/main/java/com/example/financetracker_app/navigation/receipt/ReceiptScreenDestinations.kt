package com.example.financetracker_app.navigation.receipt

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.financetracker_app.navigation.LeafScreenDestination
import com.example.financetracker_app.navigation.RootScreenDestination

object ReceiptRoot : RootScreenDestination {
    override val route = "receiptRoot"
    override val title = "Receipt"
}

object ReceiptList : LeafScreenDestination {
    override val route = "receipts"
    override val title = "Receipt List"
}

object ReceiptDetails : LeafScreenDestination {
    override val route = "receipt"
    override val title = "Receipt Details"
    const val receiptIdArg = "receipt_id"
    val routeWithArgs = "$route/{$receiptIdArg}"
    val arguments = listOf(
        navArgument(receiptIdArg) { type = NavType.StringType }
    )
}

object ReceiptCreate : LeafScreenDestination {
    override val route = "receiptCreate"
    override val title = "Receipt Create"
}
