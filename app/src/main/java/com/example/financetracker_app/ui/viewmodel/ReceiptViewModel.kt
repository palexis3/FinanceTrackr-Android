package com.example.financetracker_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.data.models.ReceiptCreate
import com.example.financetracker_app.data.remote.repository.receipt.ReceiptRepository
import com.example.financetracker_app.helper.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.financetracker_app.helper.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface ReceiptListUiState {
    data class Success(val receipts: List<Receipt>): ReceiptListUiState
    object Error: ReceiptListUiState
    object Loading: ReceiptListUiState
}

data class ReceiptListScreenUiState(
    val receiptListUiState: ReceiptListUiState
)

sealed interface ReceiptUiState {
    data class Success(val receipt: Receipt): ReceiptUiState
    object Error: ReceiptUiState
    object Loading: ReceiptUiState
}

data class ReceiptScreenUiState(
    val receiptUiState: ReceiptUiState
)

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val receiptRepository: ReceiptRepository
): ViewModel() {

    val receiptListState: StateFlow<ReceiptListScreenUiState> = receiptRepository.getAllReceipts().asResult().map { result ->
        val receiptListUiState: ReceiptListUiState = when (result) {
            is Result.Success -> ReceiptListUiState.Success(result.data)
            is Result.Loading -> ReceiptListUiState.Loading
            is Result.Error -> ReceiptListUiState.Error
        }
        ReceiptListScreenUiState(receiptListUiState)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ReceiptListScreenUiState(ReceiptListUiState.Loading)
    )

    private val _receiptState = MutableStateFlow(ReceiptScreenUiState(ReceiptUiState.Loading))
    val receiptState = _receiptState.asStateFlow()

    private val _createReceiptFlow = MutableSharedFlow<Boolean>()
    val createReceiptFlow = _createReceiptFlow.asSharedFlow()

    fun getReceipt(id: String) {
        viewModelScope.launch {
            receiptRepository.getReceipt(id).asResult().collect { result ->
                val receiptState = when (result) {
                    is Result.Success -> ReceiptUiState.Success(result.data)
                    is Result.Loading -> ReceiptUiState.Loading
                    is Result.Error -> ReceiptUiState.Error
                }
                _receiptState.value = ReceiptScreenUiState(receiptState)
            }
        }
    }

    fun createReceipt(receiptCreate: ReceiptCreate) {
        viewModelScope.launch {
            val response = receiptRepository.createReceipt(receiptCreate)
            _createReceiptFlow.emit(response)
        }
    }
}