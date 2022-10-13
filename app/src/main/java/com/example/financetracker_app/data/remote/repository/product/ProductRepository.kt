package com.example.financetracker_app.data.remote.repository.product

import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    fun getProduct(id: String): Flow<Product>
    suspend fun deleteProduct(id: String): Response<Unit>
    suspend fun updateProduct(productUpdate: ProductUpdate): Response<Unit>
    fun createProduct(productCreate: ProductCreate): Flow<Product>
}