package com.example.financetracker_app.ui.viewmodel.product

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.StoreCreate
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

    private val _categoryInput = MutableStateFlow(InputData())
    val categoryInput = _categoryInput.asStateFlow()

    private val _storeInput = MutableStateFlow(InputData())
    val storeInput = _storeInput.asStateFlow()

    private val _screenEvent = MutableStateFlow(ProductCreateInputScreenEventWrapper())
    val screenEvent = _screenEvent.asStateFlow()

    val inputDataValid =
        combine(nameInput, priceInput, categoryInput, storeInput) { name, price, category, store ->
            val nameValid = name.item.isNotEmpty() && name.errorId == null
            val priceValid = price.item.toDoubleOrNull() != null && name.errorId == null
            val categoryValid = category.item.isNotEmpty() && category.errorId == null
            val storeValid = store.item.isNotEmpty() && store.errorId == null

            nameValid && priceValid && categoryValid && storeValid
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

    fun onCategoryInput(input: String) {
        val errorId = if (input.length < 2) R.string.category_input_error else null
        _categoryInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onStoreInput(input: String) {
        val errorId = if (input.length < 2) R.string.store_input_error else null
        _storeInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onContinueClick() {
        val screenEvent = ScreenEvent.ScreenEventWithContent(
            ProductCreate(
                name = nameInput.value.item,
                price = priceInput.value.item.toFloat(),
                category = categoryInput.value.item,
                store = StoreCreate(name = storeInput.value.item)
            )
        )
        _screenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
    }
}
