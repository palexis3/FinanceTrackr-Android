package com.example.financetracker_app.data.remote.repository.product

import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    fun getProduct(id: String): Flow<Product>
    suspend fun deleteProduct(id: String): Boolean
    suspend fun updateProduct(productUpdate: ProductUpdate): Boolean
    suspend fun createProduct(productCreate: ProductCreate): Boolean
}
