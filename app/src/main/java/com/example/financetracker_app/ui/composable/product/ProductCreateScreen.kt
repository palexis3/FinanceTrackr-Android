package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.financetracker_app.ui.composable.common.EmittableDropDownMenu
import com.example.financetracker_app.ui.composable.common.EmittableTextField
import com.example.financetracker_app.ui.composable.common.SubScreenTitle
import com.example.financetracker_app.ui.viewmodel.product.ProductCreateValidationViewModel
import com.example.financetracker_app.ui.viewmodel.product.ProductViewModel

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ProductCreateScreen(
    closeScreen: () -> Unit,
    showSnackbar: (String, String) -> Unit,
    goToImageScreen: (String) -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    inputValidationViewModel: ProductCreateValidationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val productCreateInputScreenEvent by inputValidationViewModel.screenEvent.collectAsStateWithLifecycle()
    val productCreateApiScreenEvent by productViewModel.productCreateApiScreenEvent.collectAsStateWithLifecycle()

    val productCategoryList = listOf("FRUITS", "BEAUTY", "DAIRY", "POULTRY", "DEODORANT", "SOAP", "VEGETABLES", "SEAFOOD", "CLOTHING", "PANTRY")
    val storeCategoryList = listOf("GROCERY", "SPECIALTY", "DEPARTMENT", "WAREHOUSE", "DISCOUNT", "CONVENIENCE", "RESTAURANT")
    val quantityList = listOf("1", "2", "3", "4", "5")
    val timeIntervalList = listOf("DAY", "WEEK", "MONTH", "YEAR")

    LaunchedEffect(productCreateInputScreenEvent) {
        // hide the keyboard when we know the screen event has been changed from the `continue` button
        keyboardController?.hide()
        when (val screenEvent = productCreateInputScreenEvent.screenEvent) {
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            is ScreenEvent.ScreenEventWithContent -> {
                productViewModel.createProduct(screenEvent.item)
            }
            else -> {}
        }
    }

    LaunchedEffect(productCreateApiScreenEvent) {
        when (val screenEvent = productCreateApiScreenEvent.screenEvent) {
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            is ScreenEvent.GoToNextScreen -> {
                val productId = screenEvent.args[0]
                goToImageScreen(productId)
            }
            else -> {}
        }
    }

    val name by inputValidationViewModel.nameInput.collectAsStateWithLifecycle()
    val price by inputValidationViewModel.priceInput.collectAsStateWithLifecycle()
    val store by inputValidationViewModel.storeInput.collectAsStateWithLifecycle()
    val storeCategory by inputValidationViewModel.storeCategoryInput.collectAsStateWithLifecycle()
    val productCategory by inputValidationViewModel.productCategoryInput.collectAsStateWithLifecycle()
    val quantity by inputValidationViewModel.productQuantityInput.collectAsStateWithLifecycle()
    val timeIntervalType by inputValidationViewModel.timeIntervalTypeInput.collectAsStateWithLifecycle()
    val timeIntervalNum by inputValidationViewModel.timeIntervalNumInput.collectAsStateWithLifecycle()
    val inputsValid by inputValidationViewModel.inputDataValid.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = closeScreen) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(Modifier.width(8.dp))
            SubScreenTitle(title = stringResource(R.string.product_create))
        }

        Spacer(Modifier.height(12.dp))

        EmittableTextField(
            inputData = name,
            onValueChange = inputValidationViewModel::onNameChange,
            label = "Name"
        )
        Spacer(Modifier.height(8.dp))

        EmittableTextField(
            inputData = price,
            onValueChange = inputValidationViewModel::onPriceChange,
            label = "Price",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        EmittableDropDownMenu(
            inputData = quantity,
            label = "Quantity",
            listOfOptions = quantityList,
            onValueChange = inputValidationViewModel::onQuantityChange
        )
        Spacer(Modifier.height(8.dp))

        EmittableDropDownMenu(
            inputData = productCategory,
            label = "Product Category",
            listOfOptions = productCategoryList,
            onValueChange = inputValidationViewModel::onProductCategoryChange
        )
        Spacer(Modifier.height(8.dp))

        EmittableTextField(
            inputData = store,
            onValueChange = inputValidationViewModel::onStoreChange,
            label = "Store"
        )
        Spacer(modifier = Modifier.height(8.dp))

        EmittableDropDownMenu(
            inputData = storeCategory,
            label = "Store Category",
            listOfOptions = storeCategoryList,
            onValueChange = inputValidationViewModel::onStoreCategoryChange
        )
        Spacer(Modifier.height(8.dp))

        Text("Expiration from now", style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(2.dp))
        EmittableTextField(
            inputData = timeIntervalNum,
            onValueChange = inputValidationViewModel::onTimeIntervalNumChange,
            label = "Time Interval Num",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(8.dp))

        EmittableDropDownMenu(
            inputData = timeIntervalType,
            label = "Time Interval Type",
            listOfOptions = timeIntervalList,
            onValueChange = inputValidationViewModel::onTimeIntervalTypeChange
        )

        Spacer(Modifier.height(28.dp))

        Button(onClick = inputValidationViewModel::onContinueClick, enabled = inputsValid) {
            Text("Continue")
        }
    }
}
