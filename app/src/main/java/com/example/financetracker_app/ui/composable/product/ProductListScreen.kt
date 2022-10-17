package com.example.financetracker_app.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.ui.theme.LightBlue
import com.example.financetracker_app.ui.viewmodel.ProductListUiState
import com.example.financetracker_app.ui.viewmodel.ProductViewModel

private val DefaultPadding = 12.dp
private val MediumPadding = 16.dp

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState: ProductListUiState by viewModel.productListState.collectAsStateWithLifecycle()

    Column {
        ScreenTitle(title = R.string.product_list)
        Divider(Modifier.height(1.dp))
        ProductStateItem(uiState = uiState)
    }
}

@Composable
private fun ProductStateItem(uiState: ProductListUiState) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(
            top = MediumPadding,
            bottom = MediumPadding
        ),
        state = rememberLazyListState()
    ) {
        when (uiState) {
            ProductListUiState.Error -> {
                item {
                    ErrorTitle(title = R.string.product_list_error)
                }
            }
            ProductListUiState.Loading -> {
                item {
                    LoadingIcon()
                }
            }
            is ProductListUiState.Success -> {
                items(uiState.products) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductCard(product: Product, modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(DefaultPadding)
    ) {
        Row(
            modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Todo: Add image from imageUrl parameter
            Text(text = product.name, style = MaterialTheme.typography.h5)
            Chip(
                onClick = {},
                border = BorderStroke(
                    ChipDefaults.OutlinedBorderSize,
                    Color.Green
                ),
                colors = ChipDefaults.chipColors(
                    backgroundColor = LightBlue,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(24.dp)
                    .padding(2.dp)
            ) {
                Text(text = product.category)
            }
        }
        Spacer(Modifier.height(4.dp))

        val formattedAmount = "$${product.price}"
        val expirationDate = "Expires on: ${product.createdAt}"

        Text(text = formattedAmount, style = MaterialTheme.typography.subtitle2)
        Spacer(Modifier.height(4.dp))
        Text(text = expirationDate, style = MaterialTheme.typography.subtitle2)
    }
    CommonDivider()
}