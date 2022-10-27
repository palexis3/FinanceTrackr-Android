package com.example.financetracker_app.ui.composable.receipt

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.ui.composable.common.ErrorTitle
import com.example.financetracker_app.ui.composable.common.LoadingIcon
import com.example.financetracker_app.ui.composable.common.SubScreenTitle
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptUiState
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ReceiptDetailsScreen(
    receiptId: String,
    receiptViewModel: ReceiptViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        receiptViewModel.getReceipt(receiptId)
    }

    val uiState by receiptViewModel.receiptState.collectAsStateWithLifecycle()
    ShowReceiptDetailsState(uiState)
}

@Composable
fun ShowReceiptDetailsState(
    uiState: ReceiptUiState
) {
    when (uiState) {
        is ReceiptUiState.Loading -> {
            Column(Modifier.fillMaxSize()) {
                LoadingIcon()
            }
        }
        is ReceiptUiState.Error -> {
            Column(Modifier.fillMaxSize()) {
                ErrorTitle(title = R.string.receipt_details_error)
            }
        }
        is ReceiptUiState.Success -> {
            ReceiptDetailsCard(receipt = uiState.receipt)
        }
    }
}

@Composable
fun ReceiptDetailsCard(receipt: Receipt) {
    // TODO: Add image on top of receipt details card
    Card {
        Column(Modifier.padding(12.dp)) {
            SubScreenTitle(title = receipt.title)
            Spacer(Modifier.height(16.dp))

            val formattedPrice = "$${receipt.price}"
            Text(text = formattedPrice, style = MaterialTheme.typography.body1)

            Spacer(Modifier.height(8.dp))

            val store = "Store: ${receipt.storeId}"
            Text(text = store, style = MaterialTheme.typography.body1)

            Spacer(Modifier.height(8.dp))

            val createAt = "Created at: ${receipt.createdAt}"
            Text(text = createAt, style = MaterialTheme.typography.body1)
        }
    }
}
