package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.helper.ScreenEvent
import com.example.financetracker_app.ui.composable.common.ErrorTitle
import com.example.financetracker_app.ui.composable.common.LoadingIcon
import com.example.financetracker_app.ui.composable.common.SubScreenTitle
import com.example.financetracker_app.ui.viewmodel.product.ProductDetailsUiState
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductDetailsScreen(
    productId: String,
    goToUpdateScreen: () -> Unit,
    closeScreen: () -> Unit,
    showSnackbar: (String, String) -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val showDeletionDialog = remember { mutableStateOf(false) }

    val uiState: ProductDetailsUiState by viewModel.productDetailsState.collectAsStateWithLifecycle()
    val productDeleteApiScreenEvent by viewModel.productDeleteApiScreenEvent.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = productId) {
        viewModel.getProduct(productId)
    }

    LaunchedEffect(productDeleteApiScreenEvent) {
        when (val screenEvent = productDeleteApiScreenEvent.screenEvent) {
            is ScreenEvent.CloseScreen -> closeScreen.invoke()
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            else -> {}
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                goToUpdateScreen.invoke()
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Product")
            }
            Spacer(Modifier.width(4.dp))
            Button(onClick = {
                showDeletionDialog.value = true
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete product")
            }
        }

        ShowProductDetailsUiState(uiState = uiState)

        if (showDeletionDialog.value) {
            DeleteAlertDialog(
                dialogVisibility = { visibility ->
                    showDeletionDialog.value = visibility
                },
                deletionConfirmation = { confirmation ->
                    if (confirmation) {
                        viewModel.deleteProduct(productId)
                    }
                }
            )
        }
    }
}

@Composable
private fun ShowProductDetailsUiState(
    uiState: ProductDetailsUiState
) {
    when (uiState) {
        ProductDetailsUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                ErrorTitle(title = R.string.product_details_error)
            }
        }
        ProductDetailsUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                LoadingIcon()
            }
        }
        is ProductDetailsUiState.Success -> {
            ProductDetailsCard(
                product = uiState.product
            )
        }
    }
}

@Composable
fun ProductDetailsCard(
    product: Product
) {
    // TODO: Add an image above the details card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            SubScreenTitle(product.name)
            Spacer(Modifier.height(8.dp))

            val createdAt = "Created at: ${product.createdAt}"
            val category = "Category: ${product.category}"
            val amountText = "$${product.price}"

            Text(text = amountText, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = createdAt, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = category, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DeleteAlertDialog(dialogVisibility: (Boolean) -> Unit, deletionConfirmation: (Boolean) -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { dialogVisibility.invoke(false) },
        title = { Text(context.getString(R.string.product_delete_confirmation)) },
        confirmButton = {
            Button(onClick = {
                deletionConfirmation.invoke(true)
                dialogVisibility.invoke(false)
            }) {
                Text(context.getString(R.string.delete))
            }
        },
        dismissButton = {
            Button(onClick = {
                deletionConfirmation.invoke(false)
                dialogVisibility.invoke(false)
            }) {
                Text(context.getString(R.string.close))
            }
        }
    )
}
