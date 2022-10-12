package com.example.financetracker_app.data.remote.repository.images

import android.net.Uri
import okhttp3.MultipartBody
import retrofit2.Response

interface ImageRepository {
    fun createImage(type: String, id: String, imageUri: Uri?): Response<Unit>
}