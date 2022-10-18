package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.ui.composable.ErrorTitle
import com.example.financetracker_app.ui.composable.LoadingIcon
import com.example.financetracker_app.ui.composable.SubScreenTitle
import com.example.financetracker_app.ui.viewmodel.product.ProductDetailsUiState
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductDetailsScreen(
    id: String,
    viewModel: ProductViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getProduct(id)
    }

    val uiState: ProductDetailsUiState by viewModel.productDetailsState.collectAsStateWithLifecycle()
    ShowProductDetailsUiState(uiState = uiState)
}

@Composable
private fun ShowProductDetailsUiState(uiState: ProductDetailsUiState) {
    when (uiState) {
        ProductDetailsUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                ErrorTitle(title = R.string.product_details_error)
            }
        }
        ProductDetailsUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                LoadingIcon()
            }
        }
        is ProductDetailsUiState.Success -> {
            ProductDetailsCard(product = uiState.product)
        }
    }
}

@Composable
fun ProductDetailsCard(product: Product) {
    // TODO: Add an image above the details card
    Card {
        Column {
            Column(Modifier.padding(12.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        SubScreenTitle(product.name)
                        val amountText = "$${product.price}"
                        Text(
                            text = amountText,
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                val createdAt = "Created at: ${product.createdAt}"
                val category = "Category: ${product.category}"
                Text(text = createdAt, style = MaterialTheme.typography.body1)
                Text(text = category, style = MaterialTheme.typography.body1)
            }
        }
    }
}
