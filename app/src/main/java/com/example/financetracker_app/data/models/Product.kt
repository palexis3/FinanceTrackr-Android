package com.example.financetracker_app.data.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Product(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String? = null,
    val createdAt: String,
    @Json(name = "productCategory")
    val category: String
) : Parcelable

@JsonClass(generateAdapter = true)
data class ProductCreate(
    val name: String,
    val price: Float,
    val productExpiration: ProductExpiration,
    val store: StoreCreate,
    val category: String
)

@JsonClass(generateAdapter = true)
data class ProductUpdate(
    val id: String,
    val name: String? = null,
    val price: Float? = null,
    val productExpiration: ProductExpiration? = null
)

@JsonClass(generateAdapter = true)
data class ProductExpiration(
    val quantity: Int,
    val expirationFromNow: FromNow
)

data class ProductListResponse(
    val items: List<Product>,
    val total: Int
)
