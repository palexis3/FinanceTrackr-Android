package com.example.financetracker_app.data.remote.repository.images

import android.util.Log
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: FinanceTrackrApi
) : ImageRepository {

    companion object {
        private const val JPG = "jpg"
        private const val PNG = "png"
        private const val JPEG = "jpeg"
    }

    private fun getImageFileExtension(file: File): String {
        val imageExtensionSuffix = when (file.name.lowercase().split(".").last()) {
            JPG -> JPG
            PNG -> PNG
            JPEG -> JPEG
            else -> ""
        }
        return "image/$imageExtensionSuffix"
    }

    override suspend fun createImage(type: String, itemId: String, file: File): Boolean {
        return try {
            val image = MultipartBody.Part.createFormData(
                "image",
                file.path.split("/").last(),
                file.asRequestBody(getImageFileExtension(file).toMediaTypeOrNull())
            )
            val response = api.uploadImage(type, itemId, image)
            response.isSuccessful
        } catch (exception: Exception) {
            Log.d("ImageRepositoryImpl", "createImage exception: $exception")
            false
        }
    }
}
