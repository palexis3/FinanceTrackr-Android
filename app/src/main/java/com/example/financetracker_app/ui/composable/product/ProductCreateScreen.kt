package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.helper.ScreenEvent
import com.example.financetracker_app.ui.composable.ScreenTitle
import com.example.financetracker_app.ui.viewmodel.product.InputData
import com.example.financetracker_app.ui.viewmodel.product.ProductCreateValidationViewModel
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ProductCreateScreen(
    closeScreen: () -> Unit,
    showSnackbar: (String, String) -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    inputValidationViewModel: ProductCreateValidationViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val productCreateInputScreenEvent by inputValidationViewModel.screenEvent.collectAsStateWithLifecycle()
    val productCreateApiScreenEvent by productViewModel.createProductScreenEvent.collectAsStateWithLifecycle()

    LaunchedEffect(productCreateInputScreenEvent) {
        productCreateInputScreenEvent.screenEvent?.let { screenEventWrapper ->
            // hide the keyboard when we know the screen event has been changed from the `continue` button
            keyboardController?.hide()
            // Note: it's okay to cast as ScreenEvent.ScreenEventWithContent type since it's the only
            // one being used in the input validation view-model
            val product = (screenEventWrapper as ScreenEvent.ScreenEventWithContent).item
            productViewModel.createProduct(product)
        }
    }

    LaunchedEffect(productCreateApiScreenEvent) {
        when (val screenEvent = productCreateApiScreenEvent.screenEvent) {
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            ScreenEvent.CloseScreen -> {
                closeScreen()
            }
            else -> {}
        }
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = closeScreen) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            ScreenTitle(title = R.string.product_create)
        }
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
