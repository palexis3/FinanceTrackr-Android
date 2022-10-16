package com.example.financetracker_app.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Product
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
        ProductStateReducer(uiState = uiState)
    }
}

@Composable
private fun ProductStateReducer(uiState: ProductListUiState) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(
            top = MediumPadding,
            bottom = MediumPadding
        )
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

@Composable
private fun ProductCard(product: Product) {
    Card(elevation = 2.dp) {
        Column {
            Row(
                Modifier
                    .padding(DefaultPadding)
                    .fillMaxWidth()
            ) {
                // Todo: Add image from imageUrl parameter
                Text(text = product.name, style = MaterialTheme.typography.body2)
                Box(modifier = Modifier.clip(RectangleShape)) {
                    Text(text = product.category, style = MaterialTheme.typography.caption)
                }
            }
            Spacer(Modifier.height(2.dp))

            val formattedAmount = "$" + "${product.price}"
            val expirationDate = "Expires at: ${product.createdAt}"

            Row(
                Modifier
                    .padding(DefaultPadding)
                    .fillMaxWidth()
            ) {
                Text(text = formattedAmount, style = MaterialTheme.typography.subtitle2)
                Text(text = expirationDate, style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}
