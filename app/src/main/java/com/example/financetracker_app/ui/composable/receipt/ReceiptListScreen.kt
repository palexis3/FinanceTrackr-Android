package com.example.financetracker_app.ui.composable.receipt

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.financetracker_app.ui.composable.common.ScreenTitle
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptListUiState
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptViewModel

private val MediumPadding = 16.dp

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ReceiptListScreen(
    goToReceiptDetailsScreen: (String) -> Unit,
    receiptViewModel: ReceiptViewModel = hiltViewModel()
) {

    val uiState by receiptViewModel.receiptListState.collectAsStateWithLifecycle()

    Column(Modifier.padding(12.dp)) {
        ScreenTitle(title = R.string.receipt_list)
        Spacer(Modifier.height(4.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Row {
                Text("Add")
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Receipt")
            }
        }
        Spacer(Modifier.height(12.dp))
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
                    Log.d("ReceiptListScreen", "Loading Icon shown")
                    LoadingIcon()
                }
            }
            is ReceiptListUiState.Success -> {
                if (uiState.receipts.isEmpty()) {
                    Log.d("ReceiptListScreen", "uiState.receipts: ${uiState.receipts}")
                    item {
                        Text(
                            text = stringResource(id = R.string.receipt_list_empty),
                            style = MaterialTheme.typography.caption
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
    Card {
        Column(
            modifier
                .clickable { goToReceiptDetailsScreen(receipt.id) }
        ) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Todo: Add image from imageUrl
                Text(text = receipt.title, style = MaterialTheme.typography.h5)
            }
            Spacer(Modifier.height(4.dp))

            val formattedAmount = "$${receipt.price}"
            Text(text = formattedAmount, style = MaterialTheme.typography.subtitle2)
        }
    }
}
