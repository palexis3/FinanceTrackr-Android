package com.example.financetracker_app.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Receipt(
    val id: String,
    val title: String,
    val price: Float,
    val imageUrl: String? = null,
    val storeId: String,
    val createdAt: String
)

@JsonClass(generateAdapter = true)
data class ReceiptCreate(
    val title: String,
    val price: Float,
    val store: StoreCreate
)

data class ReceiptListResponse(
    val items: List<Receipt>,
    val total: Int
)
