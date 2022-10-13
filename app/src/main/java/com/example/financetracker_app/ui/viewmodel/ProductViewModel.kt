package com.example.financetracker_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.remote.repository.product.ProductRepository
import com.example.financetracker_app.helper.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.financetracker_app.helper.Result
import kotlinx.coroutines.flow.StateFlow

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

    private val _productListState = MutableStateFlow(ProductListScreenUiState(ProductListUiState.Loading))
    val productListState = _productListState.asStateFlow()

    private val _productState = MutableStateFlow(ProductScreenUiState(ProductUiState.Loading))
    val productState = _productState.asStateFlow()

    private val _createProductState = MutableStateFlow(false)
    val createProductState: StateFlow<Boolean>
        get() = _createProductState

   init {
      viewModelScope.launch {
          productRepository.getAllProducts().asResult()
              .collect { result ->
                  val productListState = when (result) {
                      is Result.Success -> ProductListUiState.Success(result.data)
                      is Result.Loading -> ProductListUiState.Loading
                      is Result.Error -> ProductListUiState.Error
                  }
                  _productListState.value = ProductListScreenUiState(productListState)
              }
      }
   }

    fun fetchProduct(id: String) {
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
            _createProductState.value = productRepository.createProduct(productCreate)
        }
    }
}