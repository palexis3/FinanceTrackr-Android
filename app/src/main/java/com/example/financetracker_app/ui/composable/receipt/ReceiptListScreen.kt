package com.example.financetracker_app.ui.composable.receipt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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

private val MediumPadding = 16.dp

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ReceiptListScreen(
    closeScreen: () -> Unit,
    goToReceiptCreateScreen: () -> Unit,
    goToReceiptDetailsScreen: (String) -> Unit,
    receiptViewModel: ReceiptViewModel = hiltViewModel()
) {

    val uiState by receiptViewModel.receiptListState.collectAsStateWithLifecycle()

    Column(Modifier.padding(12.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = closeScreen) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
            }

            Spacer(Modifier.width(8.dp))
            SubScreenTitle(title = stringResource(R.string.receipt_list))
        }

        Spacer(Modifier.height(2.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = goToReceiptCreateScreen) {
                Row {
                    Text("Add")
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Receipt")
                }
            }
        }

        Spacer(Modifier.height(8.dp))

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
            top = MediumPadding,
            bottom = MediumPadding
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { goToReceiptDetailsScreen(receipt.id) },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
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
    }
    Spacer(Modifier.height(8.dp))
}
