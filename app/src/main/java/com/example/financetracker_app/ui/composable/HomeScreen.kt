package com.example.financetracker_app.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.financetracker_app.R

private val HomeDefaultPadding = 8.dp

@Composable
fun HomeScreen(
    onClickSeeAllProducts: () -> Unit = {},
    onClickSeeAllReceipts: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(HomeDefaultPadding)
            .semantics { contentDescription = "Home Screen" }
    ) {
        ScreenTitle(title = R.string.finance_trackr)
        Spacer(Modifier.height(24.dp))
        ProductRow(onClickSeeAllProducts = onClickSeeAllProducts)
        Divider(Modifier.height(2.dp))
        ReceiptRow(onClickSeeAllReceipts = onClickSeeAllReceipts)
        Divider(Modifier.height(2.dp))
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
