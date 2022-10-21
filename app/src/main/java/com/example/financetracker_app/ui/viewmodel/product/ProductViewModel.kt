package com.example.financetracker_app.ui.viewmodel.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import com.example.financetracker_app.data.remote.repository.product.ProductRepository
import com.example.financetracker_app.helper.Result
import com.example.financetracker_app.helper.ScreenEvent
import com.example.financetracker_app.helper.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

data class ProductCreateScreenEventWrapper(
    val screenEvent: ScreenEvent<Nothing>? = null
)

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

    private val _productDetailsState =
        MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val productDetailsState = _productDetailsState.asStateFlow()

    private val _createProductScreenEvent = MutableStateFlow(ProductCreateScreenEventWrapper())
    val createProductScreenEvent = _createProductScreenEvent.asStateFlow()

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
                    _productDetailsState.update { productState }
                }
        }
    }

    fun createProduct(productCreate: ProductCreate) {
        viewModelScope.launch {
            val wasCreateProductSuccessful = productRepository.createProduct(productCreate)
            val screenEvent = if (wasCreateProductSuccessful) {
                ScreenEvent.CloseScreen
            } else {
                ScreenEvent.ShowSnackbar(stringId = R.string.product_create_error)
            }
            _createProductScreenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
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
