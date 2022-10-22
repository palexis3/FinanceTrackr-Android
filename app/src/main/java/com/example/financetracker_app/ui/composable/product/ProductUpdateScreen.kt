package com.example.financetracker_app.ui.composable.product

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@Composable
fun ProductUpdateScreen(
    product: Product,
    closeScreen: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
}
