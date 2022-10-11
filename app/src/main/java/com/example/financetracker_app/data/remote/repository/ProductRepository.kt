package com.example.financetracker_app.data.remote.repository

import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    fun getProduct(id: String): Flow<Product>
    fun deleteProduct(id: String): Flow<Response<String>>
    fun updateProduct(productUpdate: ProductUpdate): Flow<Response<String>>
    fun createProduct(productCreate: ProductCreate): Flow<Product>
}