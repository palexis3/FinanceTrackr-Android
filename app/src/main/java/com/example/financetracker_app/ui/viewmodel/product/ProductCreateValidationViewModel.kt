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
            val nameValid = !name.item.isNullOrEmpty() && name.errorId == null
            val priceValid = price.item != null && price.errorId == null
            val productCategoryValid =
                !productCategory.item.isNullOrEmpty() && productCategory.errorId == null
            val storeValid = !store.item.isNullOrEmpty() && store.errorId == null
            val storeCategoryValid =
                !storeCategory.item.isNullOrEmpty() && storeCategory.errorId == null
            val productQuantityValid =
                productQuantity.item != null && productQuantity.errorId == null
            val timeIntervalTypeValid =
                !timeIntervalType.item.isNullOrEmpty() && timeIntervalType.errorId == null
            val timeIntervalNumValid =
                timeIntervalNum.item != null && timeIntervalNum.errorId == null

            nameValid && priceValid && productCategoryValid && storeValid && storeCategoryValid && productQuantityValid && timeIntervalTypeValid && timeIntervalNumValid
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onNameChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.name_input_error else null
        _nameInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onPriceChange(input: String) {
        val errorId = if (input.toDoubleOrNull() == null) R.string.price_input_error else null
        _priceInput.update { inputData ->
            inputData.copy(
                item = input.toFloat(),
                errorId = errorId
            )
        }
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
        val screenEvent = when (val productCreate = getProductCreateModel()) {
            null -> ScreenEvent.ShowSnackbar(R.string.product_create_error)
            else -> ScreenEvent.ScreenEventWithContent(productCreate)
        }
        _screenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
    }

    private fun getProductCreateModel(): ProductCreate? {
        val name = nameInput.value.item
        val price = priceInput.value.item
        val category = productCategoryInput.value.item
        val storeName = storeInput.value.item
        val storeCategory = storeCategoryInput.value.item
        val quantity = productQuantityInput.value.item
        val timeIntervalNum = timeIntervalNumInput.value.item
        val timeIntervalType = timeIntervalTypeInput.value.item

        return if (name != null &&
            price != null &&
            category != null &&
            storeName != null &&
            storeCategory != null &&
            quantity != null &&
            timeIntervalNum != null &&
            timeIntervalType != null
        ) {
            ProductCreate(
                name = name,
                price = price,
                category = category,
                store = StoreCreate(
                    name = storeName,
                    category = storeCategory
                ),
                productExpiration = ProductExpiration(
                    quantity = quantity,
                    expirationFromNow = FromNow(
                        numOf = timeIntervalNum,
                        timeInterval = TimeInterval.valueOf(timeIntervalType)
                    )
                )
            )
        } else {
            null
        }
    }
}
