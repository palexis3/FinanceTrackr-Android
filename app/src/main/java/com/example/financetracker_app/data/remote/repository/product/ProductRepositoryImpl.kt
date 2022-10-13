package com.example.financetracker_app.data.remote.repository.product

import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: FinanceTrackrApi
) : ProductRepository {

    override fun getAllProducts(): Flow<List<Product>> =
        flow { api.getAllProducts() }

    override fun getProduct(id: String): Flow<Product> =
        flow { api.getProduct(id) }

    override suspend fun deleteProduct(id: String): Boolean {
        val response = api.deleteProduct(id)
        return response.isSuccessful
    }

    override suspend fun updateProduct(productUpdate: ProductUpdate): Boolean {
        val response = api.updateProduct(productUpdate)
        return response.isSuccessful
    }

    override suspend fun createProduct(productCreate: ProductCreate): Boolean {
        val response = api.createProduct(productCreate)
        return response.isSuccessful
    }

}