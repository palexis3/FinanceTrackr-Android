package com.example.financetracker_app.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.financetracker_app.R

private val HomeDefaultPadding = 8.dp
private val HomeLargePadding = 16.dp

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
        Text(
            text = stringResource(R.string.finance_trackr),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(Modifier.height(HomeLargePadding))
        ProductRow(onClickSeeAllProducts = onClickSeeAllProducts)
        Spacer(Modifier.height(HomeDefaultPadding))
        ReceiptRow(onClickSeeAllReceipts = onClickSeeAllReceipts)
    }
}

@Composable
private fun ProductRow(
    title: String = stringResource(id = R.string.product),
    onClickSeeAllProducts: () -> Unit
) {
    BaseHomeRow(title = title, onClick = onClickSeeAllProducts)
}

@Composable
private fun ReceiptRow(
    title: String = stringResource(id = R.string.receipt),
    onClickSeeAllReceipts: () -> Unit
) {
    BaseHomeRow(title = title, onClick = onClickSeeAllReceipts)
}
