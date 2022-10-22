package com.example.financetracker_app.ui.composable.product

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financetracker_app.ui.viewmodel.product.ProductUpdateValidationViewModel
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@Composable
fun ProductUpdateScreen(
    closeScreen: () -> Unit,
    inputValidationViewModel: ProductUpdateValidationViewModel = hiltViewModel(),
    viewModel: ProductViewModel = hiltViewModel()
) {
}
