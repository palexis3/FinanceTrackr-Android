package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.financetracker_app.ui.composable.common.EmittableDropDownMenu
import com.example.financetracker_app.ui.composable.common.EmittableTextField
import com.example.financetracker_app.ui.viewmodel.product.ProductUpdateValidationViewModel
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductUpdateScreen(
    productId: String,
    closeScreen: () -> Unit,
    showSnackbar: (String, String) -> Unit,
    goToImageScreen: () -> Unit,
    updateValidationViewModel: ProductUpdateValidationViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    val timeIntervalList = listOf("DAY", "WEEK", "MONTH", "YEAR")

    val productUpdateInputScreenEvent by updateValidationViewModel.screenEvent.collectAsStateWithLifecycle()
    val productUpdateApiScreenEvent by productViewModel.productUpdateApiScreenEvent.collectAsStateWithLifecycle()

    LaunchedEffect(productUpdateInputScreenEvent) {
        keyboard?.hide()
        when (val screenEvent = productUpdateInputScreenEvent.screenEvent) {
            is ScreenEvent.ScreenEventWithContent -> {
                val productUpdate = screenEvent.item
                productViewModel.updateProduct(productUpdate)
            }
            else -> {}
        }
    }

    LaunchedEffect(productUpdateApiScreenEvent) {
        when (val screenEvent = productUpdateApiScreenEvent.screenEvent) {
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            ScreenEvent.CloseScreen -> closeScreen.invoke()
            else -> {}
        }
    }

    val name by updateValidationViewModel.nameInput.collectAsStateWithLifecycle()
    val price by updateValidationViewModel.priceInput.collectAsStateWithLifecycle()
    val quantity by updateValidationViewModel.quantityInput.collectAsStateWithLifecycle()
    val timeIntervalType by updateValidationViewModel.timeIntervalTypeInput.collectAsStateWithLifecycle()
    val timeIntervalNum by updateValidationViewModel.timeIntervalNumInput.collectAsStateWithLifecycle()
    val inputsValid by updateValidationViewModel.inputDataValid.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(onClick = closeScreen) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(Modifier.width(4.dp))

        Text(stringResource(id = R.string.product_update))
        Spacer(Modifier.height(8.dp))

        IconButton(onClick = { goToImageScreen.invoke() }) {
            Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = "Add Image"
            )
        }
        Spacer(Modifier.height(12.dp))

        EmittableTextField(
            inputData = name,
            onValueChange = updateValidationViewModel::onNameChange,
            label = "Name"
        )
        Spacer(Modifier.height(4.dp))

        EmittableTextField(
            inputData = price,
            onValueChange = updateValidationViewModel::onPriceChange,
            label = "Price",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(4.dp))

        Text(
            "Product Expiration section - All below must be entered together",
            style = MaterialTheme.typography.caption
        )
        Spacer(Modifier.height(2.dp))

        EmittableTextField(
            inputData = quantity,
            onValueChange = updateValidationViewModel::onQuantityChange,
            label = "Quantity",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(4.dp))

        EmittableTextField(
            inputData = timeIntervalNum,
            onValueChange = updateValidationViewModel::onTimeIntervalNumChange,
            label = "Time Interval Num"
        )
        Spacer(Modifier.height(4.dp))

        EmittableDropDownMenu(
            inputData = timeIntervalType,
            label = "Time Interval Type",
            listOfOptions = timeIntervalList,
            onValueChange = updateValidationViewModel::onTimeIntervalTypeChange
        )

        Spacer(Modifier.height(28.dp))

        Button(onClick = { updateValidationViewModel.onContinueClick(productId) }, enabled = inputsValid) {
            Text("Continue")
        }
    }
}
