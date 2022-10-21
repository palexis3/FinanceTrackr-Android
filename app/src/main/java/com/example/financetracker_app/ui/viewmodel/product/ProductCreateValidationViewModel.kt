package com.example.financetracker_app.ui.viewmodel.product

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.*
import com.example.financetracker_app.helper.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class InputData(
    val item: String = "",
    @StringRes val errorId: Int? = null
)

data class ProductCreateInputScreenEventWrapper(
    val screenEvent: ScreenEvent<ProductCreate>? = null
)

@HiltViewModel
class ProductCreateValidationViewModel @Inject constructor() : ViewModel() {

    private val _nameInput = MutableStateFlow(InputData())
    val nameInput = _nameInput.asStateFlow()

    private val _priceInput = MutableStateFlow(InputData())
    val priceInput = _priceInput.asStateFlow()

    private val _productCategoryInput = MutableStateFlow(InputData())
    val productCategoryInput = _productCategoryInput.asStateFlow()

    private val _storeInput = MutableStateFlow(InputData())
    val storeInput = _storeInput.asStateFlow()

    private val _storeCategoryInput = MutableStateFlow(InputData())
    val storeCategoryInput = _storeCategoryInput.asStateFlow()

    private val _productQuantityInput = MutableStateFlow(InputData())
    val productQuantityInput = _productQuantityInput.asStateFlow()

    private val _timeIntervalTypeInput = MutableStateFlow(InputData())
    val timeIntervalTypeInput = _timeIntervalTypeInput.asStateFlow()

    private val _timeIntervalNumInput = MutableStateFlow(InputData())
    val timeIntervalNumInput = _timeIntervalNumInput.asStateFlow()

    private val _screenEvent = MutableStateFlow(ProductCreateInputScreenEventWrapper())
    val screenEvent = _screenEvent.asStateFlow()

    val inputDataValid =
        com.example.financetracker_app.helper.combine(
            nameInput,
            priceInput,
            productCategoryInput,
            storeInput,
            storeCategoryInput,
            productQuantityInput,
            timeIntervalTypeInput,
            timeIntervalNumInput
        ) { name, price, productCategory, store, storeCategory, productQuantity, timeIntervalType, timeIntervalNum ->
            val nameValid = name.item.isNotEmpty() && name.errorId == null
            val priceValid = price.item.toDoubleOrNull() != null && price.errorId == null
            val productCategoryValid =
                productCategory.item.isNotEmpty() && productCategory.errorId == null
            val storeValid = store.item.isNotEmpty() && store.errorId == null
            val storeCategoryValid =
                storeCategory.item.isNotEmpty() && storeCategory.errorId == null
            val productQuantityValid =
                productQuantity.item.toIntOrNull() != null && productQuantity.errorId == null
            val timeIntervalTypeValid =
                timeIntervalType.item.isNotEmpty() && timeIntervalType.errorId == null
            val timeIntervalNumValid =
                timeIntervalNum.item.toLongOrNull() != null && timeIntervalNum.errorId == null

            nameValid && priceValid && productCategoryValid && storeValid && storeCategoryValid && productQuantityValid && timeIntervalTypeValid && timeIntervalNumValid
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onNameChange(input: String) {
        val errorId = if (input.length < 2) R.string.name_input_error else null
        _nameInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onPriceChange(input: String) {
        val isPriceValid = input.toDoubleOrNull() != null
        val errorId = if (isPriceValid) null else R.string.price_input_error
        _priceInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onStoreCategoryChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.dropdown_selection_error else null
        _storeCategoryInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onStoreChange(input: String) {
        val errorId = if (input.length < 2) R.string.store_input_error else null
        _storeInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onProductCategoryChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.dropdown_selection_error else null
        _productCategoryInput.update { inputData ->
            inputData.copy(
                item = input,
                errorId = errorId
            )
        }
    }

    fun onQuantityChange(input: String) {
        val errorId = if (input.toIntOrNull() == null) R.string.dropdown_selection_error else null
        _productQuantityInput.update { inputData ->
            inputData.copy(
                item = input,
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
                item = input,
                errorId = errorId
            )
        }
    }

    fun onContinueClick() {
        val screenEvent = ScreenEvent.ScreenEventWithContent(
            ProductCreate(
                name = nameInput.value.item,
                price = priceInput.value.item.toFloat(),
                category = productCategoryInput.value.item,
                store = StoreCreate(
                    name = storeInput.value.item,
                    category = storeCategoryInput.value.item
                ),
                productExpiration = ProductExpiration(
                    quantity = productQuantityInput.value.item.toInt(),
                    expirationFromNow = FromNow(
                        numOf = timeIntervalNumInput.value.item.toLong(),
                        timeInterval = TimeInterval.valueOf(timeIntervalTypeInput.value.item)
                    )
                )
            )
        )
        _screenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
    }
}
