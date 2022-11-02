package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.ui.composable.common.ErrorTitle
import com.example.financetracker_app.ui.composable.common.LoadingIcon
import com.example.financetracker_app.ui.composable.common.ScreenTitle
import com.example.financetracker_app.ui.composable.common.SubScreenTitle
import com.example.financetracker_app.ui.theme.LightBlue
import com.example.financetracker_app.ui.viewmodel.product.ProductListUiState
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    goToProductDetailsScreen: (String) -> Unit,
    goToProductCreateScreen: () -> Unit,
    closeScreen: () -> Unit
) {
    val uiState: ProductListUiState by viewModel.productListState.collectAsStateWithLifecycle()

    Column(Modifier.padding(12.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = closeScreen) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
            }
            Spacer(Modifier.width(8.dp))
            SubScreenTitle(title = stringResource(id = R.string.product_list))
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ExtendedFloatingActionButton(
                onClick = goToProductCreateScreen,
                text = { Text("Add") }
            )
        }

        Spacer(Modifier.height(8.dp))

        ShowProductsState(uiState = uiState, goToProductDetailsScreen)
    }
}

@Composable
private fun ShowProductsState(
    uiState: ProductListUiState,
    goToProductDetailsScreen: (String) -> Unit
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductCard(
    product: Product,
    goToProductDetailsScreen: (String) -> Unit
) {
    // Todo: Add image from imageUrl parameter
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { goToProductDetailsScreen(product.id) },
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = product.name, style = MaterialTheme.typography.h6)
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
            Spacer(Modifier.height(8.dp))

            val formattedAmount = "$${product.price}"
            val expirationDate = "Expires on: ${product.createdAt}"

            Text(text = formattedAmount, style = MaterialTheme.typography.subtitle2)
            Spacer(Modifier.height(4.dp))
            Text(text = expirationDate, style = MaterialTheme.typography.subtitle2)
        }
    }
    Spacer(Modifier.height(8.dp))
}
