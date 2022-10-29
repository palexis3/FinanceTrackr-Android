package com.example.financetracker_app.ui.composable.receipt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.helper.ScreenEvent
import com.example.financetracker_app.ui.composable.common.EmittableDropDownMenu
import com.example.financetracker_app.ui.composable.common.EmittableTextField
import com.example.financetracker_app.ui.composable.common.ScreenTitle
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptCreateValidationViewModel
import com.example.financetracker_app.ui.viewmodel.receipt.ReceiptViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun ReceiptCreateScreen(
    closeScreen: () -> Unit,
    showSnackbar: (String, String) -> Unit,
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    validationViewModel: ReceiptCreateValidationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    val storeCategoryList = listOf("GROCERY", "SPECIALTY", "DEPARTMENT", "WAREHOUSE", "DISCOUNT", "CONVENIENCE", "RESTAURANT")

    val receiptCreateInputScreenEvent by validationViewModel.inputScreenEvent.collectAsStateWithLifecycle()
    val receiptCreateApiScreenEvent by receiptViewModel.receiptCreateApiScreenEvent.collectAsStateWithLifecycle()

    LaunchedEffect(receiptCreateInputScreenEvent) {
        keyboard?.hide()
        when (val screenEvent = receiptCreateInputScreenEvent.screenEvent) {
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            is ScreenEvent.ScreenEventWithContent -> {
                receiptViewModel.createReceipt(screenEvent.item)
            }
            else -> {}
        }
    }

    LaunchedEffect(receiptCreateApiScreenEvent) {
        when (val screenEvent = receiptCreateApiScreenEvent.screenEvent) {
            is ScreenEvent.CloseScreen -> {
                closeScreen.invoke()
            }
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            else -> {}
        }
    }

    val title by validationViewModel.titleInput.collectAsStateWithLifecycle()
    val price by validationViewModel.priceInput.collectAsStateWithLifecycle()
    val store by validationViewModel.storeInput.collectAsStateWithLifecycle()
    val storeCategory by validationViewModel.storeCategoryInput.collectAsStateWithLifecycle()
    val inputsValid by validationViewModel.inputDataValid.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(onClick = closeScreen) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Spacer(Modifier.width(4.dp))

        ScreenTitle(title = R.string.receipt_create)
        Spacer(Modifier.height(8.dp))

        EmittableTextField(
            inputData = title,
            onValueChange = validationViewModel::onTitleChange,
            label = "Title"
        )
        Spacer(Modifier.height(4.dp))

        EmittableTextField(
            inputData = price,
            onValueChange = validationViewModel::onPriceChange,
            label = "Price",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(4.dp))

        Text("Store Information", style = MaterialTheme.typography.caption)
        EmittableTextField(
            inputData = store,
            onValueChange = validationViewModel::onStoreChange,
            label = "Store"
        )
        Spacer(Modifier.height(4.dp))

        EmittableDropDownMenu(
            inputData = storeCategory,
            label = "Store Category",
            listOfOptions = storeCategoryList,
            onValueChange = validationViewModel::onStoreCategoryChange
        )

        Spacer(Modifier.height(28.dp))

        Button(onClick = validationViewModel::onContinueClick, enabled = inputsValid) {
            Text("Continue")
        }
    }
}
