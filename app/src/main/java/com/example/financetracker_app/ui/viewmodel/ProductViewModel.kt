package com.example.financetracker_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import com.example.financetracker_app.data.remote.repository.product.ProductRepository
import com.example.financetracker_app.helper.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.financetracker_app.helper.Result
import kotlinx.coroutines.flow.*

sealed interface ProductListUiState {
    data class Success(val products: List<Product>): ProductListUiState
    object Error: ProductListUiState
    object Loading: ProductListUiState
}

data class ProductListScreenUiState(
    val productListState: ProductListUiState
)

sealed interface ProductUiState {
    data class Success(val product: Product): ProductUiState
    object Error: ProductUiState
    object Loading: ProductUiState
}

data class ProductScreenUiState(
    val productState: ProductUiState
)

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    val productListState: StateFlow<ProductListScreenUiState> = productRepository.getAllProducts().asResult().map { result ->
        val productListState: ProductListUiState = when (result) {
            is Result.Success -> ProductListUiState.Success(result.data)
            is Result.Loading -> ProductListUiState.Loading
            is Result.Error -> ProductListUiState.Error
        }
        ProductListScreenUiState(productListState)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProductListScreenUiState(ProductListUiState.Loading)
    )

    private val _productState = MutableStateFlow(ProductScreenUiState(ProductUiState.Loading))
    val productState = _productState.asStateFlow()

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
                        is Result.Success -> ProductUiState.Success(result.data)
                        is Result.Loading -> ProductUiState.Loading
                        is Result.Error -> ProductUiState.Error
                    }
                    _productState.value = ProductScreenUiState(productState)
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