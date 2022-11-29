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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

data class ProductCreateApiScreenEventWrapper(
    val screenEvent: ScreenEvent<Nothing>? = null
)

data class ProductUpdateApiScreenEventWrapper(
    val screenEvent: ScreenEvent<Nothing>? = null
)

data class ProductDeleteApiScreenEventWrapper(
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

    private val _productCreateApiScreenEvent = MutableStateFlow(ProductCreateApiScreenEventWrapper())
    val productCreateApiScreenEvent = _productCreateApiScreenEvent.asStateFlow()

    private val _productUpdateApiScreenEvent = MutableStateFlow(ProductUpdateApiScreenEventWrapper())
    val productUpdateApiScreenEvent = _productUpdateApiScreenEvent.asStateFlow()

    private val _productDeleteApiScreenEvent = MutableStateFlow(ProductDeleteApiScreenEventWrapper())
    val productDeleteApiScreenEvent = _productDeleteApiScreenEvent.asStateFlow()

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
            val product = productRepository.createProduct(productCreate)
            val screenEvent = if (product != null) {
                ScreenEvent.GoToNextScreen(listOf(product.id))
            } else {
                ScreenEvent.ShowSnackbar(stringId = R.string.product_create_error)
            }
            _productCreateApiScreenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
        }
    }

    fun updateProduct(productUpdate: ProductUpdate) {
        viewModelScope.launch {
            val wasProductUpdateSuccessful = productRepository.updateProduct(productUpdate)
            val screenEvent = if (wasProductUpdateSuccessful) {
                ScreenEvent.CloseScreen
            } else {
                ScreenEvent.ShowSnackbar(stringId = R.string.product_update_error)
            }
            _productUpdateApiScreenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            val wasProductDeleteSuccessful = productRepository.deleteProduct(id)
            val screenEvent = if (wasProductDeleteSuccessful) {
                ScreenEvent.CloseScreen
            } else {
                ScreenEvent.ShowSnackbar(stringId = R.string.product_delete_error)
            }
            _productDeleteApiScreenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
        }
    }
}
