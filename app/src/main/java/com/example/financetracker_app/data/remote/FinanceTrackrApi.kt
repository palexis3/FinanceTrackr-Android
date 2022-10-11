package com.example.financetracker_app.data.remote

import com.example.financetracker_app.data.models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface FinanceTrackrApi {

    // PRODUCTS
    @GET("product")
    suspend fun getAllProducts(): List<Product>

    @GET("product/{id}")
    suspend fun getProduct(@Path("id") id: String): Product

    @DELETE("product/delete/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<String>

    @POST("product/update")
    suspend fun updateProduct(@Body productUpdate: ProductUpdate): Response<String>

    @POST("product/create")
    suspend fun createProduct(@Body productCreate: ProductCreate): Product

    // RECEIPTS
    @GET("receipt")
    suspend fun getAllReceipts(): List<Receipt>

    @GET("receipt/{id}")
    suspend fun getReceipt(@Path("id") id: String): Receipt

    @POST("receipt/create")
    suspend fun createReceipt(@Body receiptCreate: ReceiptCreate): Receipt

    // IMAGES
    @Multipart
    @POST("{type}/{id}/image")
    suspend fun uploadImage(
        @Path("type") type: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<String>
}