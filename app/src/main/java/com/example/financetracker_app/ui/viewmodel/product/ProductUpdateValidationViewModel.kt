package com.example.financetracker_app.ui.viewmodel.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.*
import com.example.financetracker_app.helper.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ProductUpdateInputScreenEventWrapper(
    val screenEvent: ScreenEvent<ProductUpdate>? = null
)

@HiltViewModel
class ProductUpdateValidationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val product: StateFlow<Product?> = savedStateHandle.getStateFlow("product", null)
    private val productId = product.value?.id
    private val initialName = product.value?.name ?: ""
    private val initialPrice = product.value?.price ?: ""

    private val _nameInput = MutableStateFlow(InputData(item = initialName))
    val nameInput = _nameInput.asStateFlow()

    private val _priceInput = MutableStateFlow(InputData(item = initialPrice))
    val priceInput = _priceInput.asStateFlow()

    private val _quantityInput = MutableStateFlow(InputData())
    val quantityInput = _quantityInput.asStateFlow()

    private val _timeIntervalNumInput = MutableStateFlow(InputData())
    val timeIntervalNumInput = _timeIntervalNumInput.asStateFlow()

    private val _timeIntervalTypeInput = MutableStateFlow(InputData())
    val timeIntervalTypeInput = _timeIntervalTypeInput.asStateFlow()

    private val _screenEvent = MutableStateFlow(ProductUpdateInputScreenEventWrapper())
    val screenEvent = _screenEvent.asStateFlow()

    val inputDataValid = combine(
        nameInput, priceInput, quantityInput, timeIntervalNumInput, timeIntervalTypeInput
    ) { name, price, quantity, timeIntervalNum, timeIntervalType ->
        val nameValid = name.item.isNotEmpty() && name.errorId == null
        val priceValid = price.item.toDoubleOrNull() != null && price.errorId == null

        val quantityValid = quantity.item.toIntOrNull() != null && quantity.errorId == null
        val timeIntervalNumValid = timeIntervalNum.item.toIntOrNull() != null && timeIntervalNum.errorId == null
        val timeIntervalTypeValid = timeIntervalType.item.isNotEmpty() != null && timeIntervalType.errorId == null
        val productExpirationValid = quantityValid && timeIntervalNumValid && timeIntervalTypeValid

        nameValid || priceValid || productExpirationValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onNameChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.name_input_error else null
        _nameInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onPriceChange(input: String) {
        val errorId = if (input.toDoubleOrNull() == null) R.string.price_input_error else null
        _priceInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onQuantityChange(input: String) {
        val errorId = if (input.toIntOrNull() == null) R.string.dropdown_selection_error else null
        _quantityInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onTimeIntervalTypeChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.dropdown_selection_error else null
        _timeIntervalTypeInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onTimeIntervalNumChange(input: String) {
        val errorId = if (input.toIntOrNull() == null) R.string.dropdown_selection_error else null
        _timeIntervalNumInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onContinueClick() {
        val screenEvent = if (productId == null) {
            ScreenEvent.ShowSnackbar(stringId = R.string.product_update_error)
        } else {
            ScreenEvent.ScreenEventWithContent(
                ProductUpdate(
                    id = productId,
                    name = nameInput.value.item,
                    price = priceInput.value.item.toFloatOrNull(),
                    productExpiration = ProductExpiration(
                        quantity = quantityInput.value.item.toInt(),
                        expirationFromNow = FromNow(
                            numOf = timeIntervalNumInput.value.item.toLong(),
                            timeInterval = TimeInterval.valueOf(timeIntervalTypeInput.value.item)
                        )
                    )
                )
            )
        }
        _screenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
    }
}
