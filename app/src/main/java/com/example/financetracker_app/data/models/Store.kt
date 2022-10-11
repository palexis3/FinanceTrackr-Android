package com.example.financetracker_app.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Store(
    val id: String,
    val name: String,
    val category: String
)

@JsonClass(generateAdapter = true)
data class StoreCreate(
    val name: String,
    val category: String
)