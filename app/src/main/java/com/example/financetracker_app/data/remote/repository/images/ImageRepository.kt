package com.example.financetracker_app.data.remote.repository.images

import android.net.Uri

interface ImageRepository {
    suspend fun createImage(type: String, itemId: String, imageUri: Uri): Boolean
}
