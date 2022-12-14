package com.example.financetracker_app.data.remote

import com.example.financetracker_app.data.models.Product
import com.example.financetracker_app.data.models.ProductCreate
import com.example.financetracker_app.data.models.ProductListResponse
import com.example.financetracker_app.data.models.ProductUpdate
import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.data.models.ReceiptCreate
import com.example.financetracker_app.data.models.ReceiptListResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FinanceTrackrApi {
    // PRODUCTS
    @GET("/product")
    suspend fun getAllProducts(): ProductListResponse

    @GET("/product/{id}")
    suspend fun getProduct(@Path("id") id: String): Product

    @DELETE("/product/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Unit>

    @POST("/product/update")
    suspend fun updateProduct(@Body productUpdate: ProductUpdate): Response<Unit>

    @POST("/product/create")
    suspend fun createProduct(@Body productCreate: ProductCreate): Response<Product>

    // RECEIPTS

    // TODO: Refactor result type to ReceiptListResponse once backend response is fixed
    @GET("/receipt")
    suspend fun getAllReceipts(): Response<ReceiptListResponse>

    @GET("/receipt/{id}")
    suspend fun getReceipt(@Path("id") id: String): Receipt

    @POST("/receipt/create")
    suspend fun createReceipt(@Body receiptCreate: ReceiptCreate): Response<Unit>

    // IMAGES
    @Multipart
    @POST("/{type}/{id}/image")
    suspend fun uploadImage(
        @Path("type") type: String,
        @Path("id") itemId: String,
        @Part file: MultipartBody.Part
    ): Response<Unit>
}
