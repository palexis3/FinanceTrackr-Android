package com.example.financetracker_app.ui.viewmodel.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.ReceiptCreate
import com.example.financetracker_app.data.models.StoreCreate
import com.example.financetracker_app.helper.InputData
import com.example.financetracker_app.helper.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ReceiptCreateInputScreenEventWrapper(
    val screenEvent: ScreenEvent<ReceiptCreate>? = null
)

@HiltViewModel
class ReceiptCreateValidationViewModel @Inject constructor() : ViewModel() {

    private val _titleInput = MutableStateFlow(InputData<String>())
    val titleInput = _titleInput.asStateFlow()

    private val _priceInput = MutableStateFlow(InputData<String>())
    val priceInput = _priceInput.asStateFlow()

    private val _storeInput = MutableStateFlow(InputData<String>())
    val storeInput = _storeInput.asStateFlow()

    private val _storeCategoryInput = MutableStateFlow(InputData<String>())
    val storeCategoryInput = _storeCategoryInput.asStateFlow()

    private val _inputScreenEvent = MutableStateFlow(ReceiptCreateInputScreenEventWrapper())
    val inputScreenEvent = _inputScreenEvent.asStateFlow()

    val inputDataValid = combine(titleInput, priceInput, storeInput, storeCategoryInput) { title, price, store, storeCategory ->
        val titleValid = !title.item.isNullOrEmpty() && title.errorId == null
        val priceValid = price.item != null && price.errorId == null
        val storeValid = !store.item.isNullOrEmpty() && store.errorId == null
        val storeCategoryValid = !storeCategory.item.isNullOrEmpty() && storeCategory.errorId == null

        titleValid && priceValid && storeValid && storeCategoryValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    fun onTitleChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.title_input_error else null
        _titleInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onPriceChange(input: String) {
        val errorId = if (input.toFloatOrNull() == null) R.string.price_input_error else null
        _priceInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onStoreChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.store_input_error else null
        _storeInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onStoreCategoryChange(input: String) {
        val errorId = if (input.isEmpty()) R.string.dropdown_selection_error else null
        _storeCategoryInput.update { inputData -> inputData.copy(item = input, errorId = errorId) }
    }

    fun onContinueClick() {
        val screenEvent = when (val receiptCreate = getReceiptCreate()) {
            null -> ScreenEvent.ShowSnackbar(stringId = R.string.receipt_create_error)
            else -> ScreenEvent.ScreenEventWithContent(receiptCreate)
        }
        _inputScreenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
    }

    private fun getReceiptCreate(): ReceiptCreate? {
        val title = titleInput.value.item
        val price = priceInput.value.item
        val store = storeInput.value.item
        val storeCategory = storeCategoryInput.value.item

        return if (title != null && price != null && store != null && storeCategory != null) {
            ReceiptCreate(
                title = title,
                price = price.toFloat(),
                store = StoreCreate(
                    name = store,
                    category = storeCategory
                )
            )
        } else null
    }
}
