package com.example.financetracker_app.ui.composable.receipt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.ui.composable.common.ErrorTitle
import com.example.financetracker_app.ui.composable.common.LoadingIcon
import com.example.financetracker_app.ui.composable.common.SubScreenTitle
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptListUiState
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ReceiptListScreen(
    goToReceiptCreateScreen: () -> Unit,
    goToReceiptDetailsScreen: (String) -> Unit,
    receiptViewModel: ReceiptViewModel = hiltViewModel()
) {

    val uiState by receiptViewModel.receiptListState.collectAsStateWithLifecycle()

    Column(Modifier.padding(12.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ElevatedButton(onClick = goToReceiptCreateScreen) {
                Text("Add")
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Receipt")
            }
        }

        Spacer(Modifier.height(16.dp))

        ShowReceiptsState(uiState, goToReceiptDetailsScreen)
    }
}

@Composable
fun ShowReceiptsState(
    uiState: ReceiptListUiState,
    goToReceiptDetailsScreen: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp
        )
    ) {
        when (uiState) {
            ReceiptListUiState.Error -> {
                item {
                    ErrorTitle(title = R.string.receipt_list_error)
                }
            }
            ReceiptListUiState.Loading -> {
                item {
                    LoadingIcon()
                }
            }
            is ReceiptListUiState.Success -> {
                if (uiState.receipts.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = R.string.receipt_list_empty),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                } else {
                    items(uiState.receipts) { receipt ->
                        ReceiptCard(
                            receipt = receipt,
                            goToReceiptDetailsScreen = goToReceiptDetailsScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReceiptCard(
    receipt: Receipt,
    goToReceiptDetailsScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { goToReceiptDetailsScreen(receipt.id) }
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Todo: Add image from imageUrl
            SubScreenTitle(title = receipt.title)
        }

        Spacer(Modifier.height(4.dp))
        val formattedAmount = "$${receipt.price}"
        Text(text = formattedAmount, style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(4.dp))
        val store = "Store: ${receipt.storeId}"
        Text(text = store, style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(4.dp))
        val createdAt = "Created at: ${receipt.createdAt}"
        Text(text = createdAt, style = MaterialTheme.typography.bodyMedium)
    }
    Divider(Modifier.height(2.dp))
}
