package com.example.financetracker_app.data.remote.repository.product

import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductUpdate
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: FinanceTrackrApi
) : ProductRepository {

    override fun getAllProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getProduct(id: String): Flow<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(id: String): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProduct(productUpdate: ProductUpdate): Response<Unit> {
        TODO("Not yet implemented")
    }

    override fun createProduct(productCreate: ProductCreate): Flow<Product> {
        TODO("Not yet implemented")
    }


}