package com.example.financetracker_app.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.financetracker_app.R
import com.example.financetracker_app.ui.composable.common.BaseHomeRow
import com.example.financetracker_app.ui.composable.common.CommonDivider
import com.example.financetracker_app.ui.composable.common.ScreenTitle

@Composable
fun HomeScreen(
    onClickSeeAllProducts: () -> Unit = {},
    onClickSeeAllReceipts: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .semantics { contentDescription = "Home Screen" }
    ) {
        ScreenTitle(title = R.string.finance_trackr)
        Spacer(Modifier.height(24.dp))

        ProductRow(onClickSeeAllProducts = onClickSeeAllProducts)
        CommonDivider()
        ReceiptRow(onClickSeeAllReceipts = onClickSeeAllReceipts)
        CommonDivider()
    }
}

@Composable
private fun ProductRow(
    title: Int = R.string.product,
    onClickSeeAllProducts: () -> Unit
) {
    BaseHomeRow(title = title, onClick = onClickSeeAllProducts)
}

@Composable
private fun ReceiptRow(
    title: Int = R.string.receipt,
    onClickSeeAllReceipts: () -> Unit
) {
    BaseHomeRow(title = title, onClick = onClickSeeAllReceipts)
}
