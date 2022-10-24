package com.example.financetracker_app.ui.viewmodel.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.*
import com.example.financetracker_app.helper.InputData
import com.example.financetracker_app.helper.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ProductCreateInputScreenEventWrapper(
    val screenEvent: ScreenEvent<ProductCreate>? = null
)

@HiltViewModel
class ProductCreateValidationViewModel @Inject constructor() : ViewModel() {

    private val _nameInput = MutableStateFlow(InputData<String>())
    val nameInput = _nameInput.asStateFlow()

    private val _priceInput = MutableStateFlow(InputData<Float>())
    val priceInput = _priceInput.asStateFlow()

    private val _productCategoryInput = MutableStateFlow(InputData<String>())
    val productCategoryInput = _productCategoryInput.asStateFlow()

    private val _storeInput = MutableStateFlow(InputData<String>())
    val storeInput = _storeInput.asStateFlow()

    private val _storeCategoryInput = MutableStateFlow(InputData<String>())
    val storeCategoryInput = _storeCategoryInput.asStateFlow()

    private val _productQuantityInput = MutableStateFlow(InputData<Int>())
    val productQuantityInput = _productQuantityInput.asStateFlow()

    private val _timeIntervalTypeInput = MutableStateFlow(InputData<String>())
    val timeIntervalTypeInput = _timeIntervalTypeInput.asStateFlow()

    private val _timeIntervalNumInput = MutableStateFlow(InputData<Long>())
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
            val nameValid = name.item?.isEmpty() == false && name.errorId == null
            val priceValid = price.item != null && price.errorId == null
            val productCategoryValid =
                productCategory.item?.isEmpty() == false && productCategory.errorId == null
            val storeValid = store.item?.isEmpty() == false && store.errorId == null
            val storeCategoryValid =
                storeCategory.item?.isEmpty() == false && storeCategory.errorId == null
            val productQuantityValid =
                productQuantity.item != null && productQuantity.errorId == null
            val timeIntervalTypeValid =
                timeIntervalType.item?.isEmpty() == false && timeIntervalType.errorId == null
            val timeIntervalNumValid =
                timeIntervalNum.item != null && timeIntervalNum.errorId == null

            nameValid && priceValid && productCategoryValid && storeValid && storeCategoryValid && productQuantityValid && timeIntervalTypeValid && timeIntervalNumValid
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onNameChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.name_input_error else null
        _nameInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onPriceChange(input: String) {
        val isPriceValid = input.toDoubleOrNull() != null
        val errorId = if (isPriceValid) null else R.string.price_input_error
        _priceInput.update { inputData -> inputData.copy(item = input.toFloat(), errorId = errorId) }
    }

    fun onStoreCategoryChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.dropdown_selection_error else null
        _storeCategoryInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onStoreChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.store_input_error else null
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

    fun onContinueClick() {
        val screenEvent = ScreenEvent.ScreenEventWithContent(
            ProductCreate(
                name = nameInput.value.item ?: return,
                price = priceInput.value.item ?: return,
                category = productCategoryInput.value.item ?: return,
                store = StoreCreate(
                    name = storeInput.value.item ?: return,
                    category = storeCategoryInput.value.item ?: return
                ),
                productExpiration = ProductExpiration(
                    quantity = productQuantityInput.value.item ?: return,
                    expirationFromNow = FromNow(
                        numOf = timeIntervalNumInput.value.item ?: return,
                        timeInterval = TimeInterval.valueOf(timeIntervalTypeInput.value.item ?: return)
                    )
                )
            )
        )
        _screenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
    }
}
