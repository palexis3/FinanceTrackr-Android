package com.example.financetracker_app.ui.viewmodel.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.FromNow
import com.example.financetracker_app.data.models.ProductExpiration
import com.example.financetracker_app.data.models.ProductUpdate
import com.example.financetracker_app.data.models.TimeInterval
import com.example.financetracker_app.helper.InputData
import com.example.financetracker_app.helper.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProductUpdateInputScreenEventWrapper(
    val screenEvent: ScreenEvent<ProductUpdate>? = null
)

@HiltViewModel
class ProductUpdateValidationViewModel @Inject constructor() : ViewModel() {

    private val _nameInput = MutableStateFlow(InputData<String>())
    val nameInput = _nameInput.asStateFlow()

    private val _priceInput = MutableStateFlow(InputData<String>())
    val priceInput = _priceInput.asStateFlow()

    private val _quantityInput = MutableStateFlow(InputData<Int>())
    val quantityInput = _quantityInput.asStateFlow()

    private val _timeIntervalNumInput = MutableStateFlow(InputData<Long>())
    val timeIntervalNumInput = _timeIntervalNumInput.asStateFlow()

    private val _timeIntervalTypeInput = MutableStateFlow(InputData<String>())
    val timeIntervalTypeInput = _timeIntervalTypeInput.asStateFlow()

    private val _screenEvent = MutableStateFlow(ProductUpdateInputScreenEventWrapper())
    val screenEvent = _screenEvent.asStateFlow()

    val inputDataValid = combine(
        nameInput, priceInput, quantityInput, timeIntervalNumInput, timeIntervalTypeInput
    ) { name, price, quantity, timeIntervalNum, timeIntervalType ->
        val nameValid = !name.item.isNullOrEmpty() && name.errorId == null
        val priceValid = price.item != null && price.errorId == null

        // Note: product expiration is calculated with a quantity and duration that each product
        // is expected to expire, so they're all required to be entered
        val quantityValid = quantity.item != null && quantity.errorId == null
        val timeIntervalNumValid = timeIntervalNum.item != null && timeIntervalNum.errorId == null
        val timeIntervalTypeValid =
            !timeIntervalType.item.isNullOrEmpty() && timeIntervalType.errorId == null
        val productExpirationValid = quantityValid && timeIntervalNumValid && timeIntervalTypeValid

        nameValid || priceValid || productExpirationValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onNameChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.name_input_error else null
        _nameInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onPriceChange(input: String) {
        val errorId = if (input.toFloatOrNull() == null) R.string.price_input_error else null
        _priceInput.update { inputData ->
            inputData.copy(
                item = input,
                errorId = errorId
            )
        }
    }

    fun onQuantityChange(input: String) {
        val errorId = if (input.toIntOrNull() == null) R.string.dropdown_selection_error else null
        _quantityInput.update { inputData ->
            inputData.copy(
                item = input.toInt(),
                errorId = errorId
            )
        }
    }

    fun onTimeIntervalTypeChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.dropdown_selection_error else null
        _timeIntervalTypeInput.update { inputData ->
            inputData.copy(
                item = input,
                errorId = errorId
            )
        }
    }

    fun onTimeIntervalNumChange(input: String) {
        val errorId = if (input.toLongOrNull() == null) R.string.dropdown_selection_error else null
        _timeIntervalNumInput.update { inputData ->
            inputData.copy(
                item = input.toLong(),
                errorId = errorId
            )
        }
    }

    fun onContinueClick(productId: String) {
        val quantity = quantityInput.value.item
        val timeIntervalNum = timeIntervalNumInput.value.item
        val timeIntervalType = timeIntervalTypeInput.value.item

        val productExpiration =
            if (quantity != null && timeIntervalNum != null && timeIntervalType != null) {
                ProductExpiration(
                    quantity = quantity,
                    expirationFromNow = FromNow(
                        numOf = timeIntervalNum,
                        timeInterval = TimeInterval.valueOf(timeIntervalType)
                    )
                )
            } else null

        val screenEvent = ScreenEvent.ScreenEventWithContent(
            ProductUpdate(
                id = productId,
                name = nameInput.value.item,
                price = priceInput.value.item?.toFloat(),
                productExpiration = productExpiration
            )
        )
        _screenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
    }
}
