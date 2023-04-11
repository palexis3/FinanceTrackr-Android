package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.ui.composable.common.ErrorTitle
import com.example.financetracker_app.ui.composable.common.LoadingIcon
import com.example.financetracker_app.ui.composable.common.SubScreenTitle
import com.example.financetracker_app.ui.viewmodel.product.ProductListUiState
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    goToProductDetailsScreen: (String, String) -> Unit,
    goToProductCreateScreen: () -> Unit
) {
    val uiState: ProductListUiState by viewModel.productListState.collectAsStateWithLifecycle()

    Column(Modifier.padding(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ElevatedButton(onClick = goToProductCreateScreen) {
                Text("Add")
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product")
            }
        }

        Spacer(Modifier.height(8.dp))

        ShowProductsState(uiState = uiState, goToProductDetailsScreen)
    }
}

@Composable
private fun ShowProductsState(
    uiState: ProductListUiState,
    goToProductDetailsScreen: (String, String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp
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
                    ProductCard(product, goToProductDetailsScreen)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCard(
    product: Product,
    goToProductDetailsScreen: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .heightIn(100.dp, 150.dp)
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { goToProductDetailsScreen(product.id, product.formattedName) },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            product.imageUrl?.let { image ->
                AsyncImage(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(125.dp),
                    model = image,
                    contentDescription = "${product.formattedName} image",
                    contentScale = ContentScale.Crop
                )
                Modifier.width(8.dp)
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                ElevatedAssistChip(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.End)
                        .height(24.dp)
                        .padding(2.dp),
                    label = { Text(text = product.category) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SubScreenTitle(title = product.formattedName, Modifier.widthIn(min = 32.dp))
                }
                Spacer(Modifier.height(4.dp))

                Text(text = product.formattedPrice, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(2.dp))
                Text(
                    text = product.formattedExpirationDate,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
