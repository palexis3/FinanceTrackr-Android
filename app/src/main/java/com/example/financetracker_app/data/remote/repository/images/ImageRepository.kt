package com.example.financetracker_app.data.remote.repository.images

import android.net.Uri
import okhttp3.MultipartBody
import retrofit2.Response

interface ImageRepository {
    suspend fun createImage(type: String, id: String, imageUri: Uri?): Response<Unit>
}