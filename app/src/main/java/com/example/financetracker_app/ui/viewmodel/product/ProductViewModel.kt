package com.example.financetracker_app.ui.viewmodel.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import com.example.financetracker_app.data.remote.repository.product.ProductRepository
import com.example.financetracker_app.helper.Result
import com.example.financetracker_app.helper.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductListUiState {
    data class Success(val products: List<Product>) : ProductListUiState
    object Error : ProductListUiState
    object Loading : ProductListUiState
}

sealed interface ProductDetailsUiState {
    data class Success(val product: Product) : ProductDetailsUiState
    object Error : ProductDetailsUiState
    object Loading : ProductDetailsUiState
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val productListState: StateFlow<ProductListUiState> = productRepository
        .getAllProducts()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> ProductListUiState.Success(result.data)
                is Result.Loading -> ProductListUiState.Loading
                is Result.Error -> ProductListUiState.Error
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductListUiState.Loading
        )

    private val _productDetailsState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val productDetailsState = _productDetailsState.asStateFlow()

    private val _createProductFlow = MutableSharedFlow<Boolean>()
    val createProductFlow = _createProductFlow.asSharedFlow()

    private val _updateProductFlow = MutableSharedFlow<Boolean>()
    val updateProductFlow = _updateProductFlow.asSharedFlow()

    private val _deleteProductFlow = MutableSharedFlow<Boolean>()
    val deleteProductFlow = _deleteProductFlow.asSharedFlow()

    fun getProduct(id: String) {
        viewModelScope.launch {
            productRepository.getProduct(id).asResult()
                .collect { result ->
                    val productState = when (result) {
                        is Result.Success -> ProductDetailsUiState.Success(result.data)
                        is Result.Loading -> ProductDetailsUiState.Loading
                        is Result.Error -> ProductDetailsUiState.Error
                    }
                    _productDetailsState.value = productState
                }
        }
    }

    fun createProduct(productCreate: ProductCreate) {
        viewModelScope.launch {
            val response = productRepository.createProduct(productCreate)
            _createProductFlow.emit(response)
        }
    }

    fun updateProduct(productUpdate: ProductUpdate) {
        viewModelScope.launch {
            val response = productRepository.updateProduct(productUpdate)
            _updateProductFlow.emit(response)
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            val response = productRepository.deleteProduct(id)
            _deleteProductFlow.emit(response)
        }
    }
}
