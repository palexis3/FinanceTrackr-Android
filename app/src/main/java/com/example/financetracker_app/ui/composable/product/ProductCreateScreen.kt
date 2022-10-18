package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.ui.composable.ScreenTitle
import com.example.financetracker_app.ui.viewmodel.product.InputData
import com.example.financetracker_app.ui.viewmodel.product.ProductCreateValidationViewModel
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductCreateScreen(
    productViewModel: ProductViewModel = hiltViewModel(),
    inputValidationViewModel: ProductCreateValidationViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
    }

    LaunchedEffect(Unit) {
    }

    val name by inputValidationViewModel.nameInput.collectAsStateWithLifecycle()
    val price by inputValidationViewModel.priceInput.collectAsStateWithLifecycle()
    val category by inputValidationViewModel.categoryInput.collectAsStateWithLifecycle()
    val store by inputValidationViewModel.storeInput.collectAsStateWithLifecycle()
    val inputsValid by inputValidationViewModel.inputDataValid.collectAsStateWithLifecycle()

    Column(
        Modifier
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ScreenTitle(title = R.string.product_create)
        Divider(Modifier.height(1.dp))

        CustomTextField(
            inputData = name,
            onValueChange = inputValidationViewModel::onNameChange,
            label = "Name"
        )
        Spacer(Modifier.height(8.dp))
        CustomTextField(
            inputData = price,
            onValueChange = inputValidationViewModel::onPriceChange,
            label = "Price",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(8.dp))
        // TODO: Convert category into some type of dropdown selection
        CustomTextField(
            inputData = category,
            onValueChange = inputValidationViewModel::onCategoryInput,
            label = "Category"
        )
        Spacer(Modifier.height(8.dp))
        CustomTextField(
            inputData = store,
            onValueChange = inputValidationViewModel::onStoreInput,
            label = "Store"
        )

        Spacer(Modifier.height(24.dp))

        Button(onClick = inputValidationViewModel::onContinueClick, enabled = inputsValid) {
            Text("Continue")
        }
    }
}

@Composable
fun CustomTextField(
    inputData: InputData,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column {
        TextField(
            value = inputData.item,
            onValueChange = { onValueChange(it) },
            isError = inputData.errorId != null,
            label = { Text(label) },
            keyboardOptions = keyboardOptions
        )
        inputData.errorId?.let {
            Text(text = stringResource(id = it))
        }
    }
}
