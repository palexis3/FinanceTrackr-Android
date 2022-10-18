package com.example.financetracker_app.ui.viewmodel.product

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*

data class InputData(
    val item: String = "",
    @StringRes val errorId: Int? = null
)

@HiltViewModel
class ProductCreateValidationViewModel : ViewModel() {

    private val _nameInput = MutableStateFlow(InputData())
    val nameInput = _nameInput.asStateFlow()

    private val _priceInput = MutableStateFlow(InputData())
    val priceInput = _priceInput.asStateFlow()

    private val _categoryInput = MutableStateFlow(InputData())
    val categoryInput = _categoryInput.asStateFlow()

    private val _storeInput = MutableStateFlow(InputData())
    val storeInput = _storeInput.asStateFlow()

    val inputDataValid = combine(_nameInput, _priceInput, _categoryInput, _storeInput) { name, price, category, store ->
        val nameValid = name.item.isNotEmpty() && name.errorId == null
        val priceValid = price.item.toDoubleOrNull() != null && name.errorId == null
        val categoryValid = category.item.isNotEmpty() && category.errorId == null
        val storeValid = store.item.isNotEmpty() && store.errorId == null

        nameValid && priceValid && categoryValid && storeValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onNameChange(input: String) {
        val errorId = if (input.length < 2) null else R.string.name_input_error
        _nameInput.value = _nameInput.value.copy(item = input, errorId = errorId)
    }

    fun onPriceChange(input: String) {
        val isPriceValid = input.toDoubleOrNull() != null
        val errorId = if (isPriceValid) null else R.string.price_input_error
        _priceInput.value = _priceInput.value.copy(item = input, errorId = errorId)
    }

    fun onCategoryInput(input: String) {
        val errorId = if (input.length < 2) null else R.string.category_input_error
        _categoryInput.value = _categoryInput.value.copy(item = input, errorId = errorId)
    }

    fun onStoreInput(input: String) {
        val errorId = if (input.length < 2) null else R.string.store_input_error
        _storeInput.value = _storeInput.value.copy(item = input, errorId = errorId)
    }

    fun onContinueClick() {}
}
