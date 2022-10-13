package com.example.financetracker_app.data.remote.repository.images

import android.net.Uri
import retrofit2.Response

interface ImageRepository {
    suspend fun createImage(type: String, id: String, imageUri: Uri?): Boolean
}