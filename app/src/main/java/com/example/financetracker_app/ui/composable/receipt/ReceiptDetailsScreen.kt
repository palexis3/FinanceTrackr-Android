package com.example.financetracker_app.ui.composable.receipt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
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
    LaunchedEffect(key1 = receiptId) {
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
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                LoadingIcon()
            }
        }
        is ReceiptUiState.Error -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
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
    Card(
        Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            receipt.imageUrl?.let { image ->
                AsyncImage(
                    model = image,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentDescription = "${receipt.title} image",
                    contentScale = ContentScale.Crop,
                )
                Spacer(Modifier.height(4.dp))
            }

            Column(modifier = Modifier.padding(12.dp)) {
                SubScreenTitle(title = receipt.title)
                Spacer(Modifier.height(12.dp))

                Text(text = receipt.formattedPrice, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))

                Text(text = receipt.formattedStore, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))

                Text(text = receipt.formattedCreatedAt, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
