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
        const val JPG = "jpg"
        const val PNG = "png"
        const val JPEG = "jpeg"
    }

    // TODO: Add image extension helper to specify and append the type before upload
    fun getImageFileExtension() {}

    override suspend fun createImage(type: String, itemId: String, file: File): Boolean {
        return try {
            val image = MultipartBody.Part.createFormData(
                "image",
                file.path.split("/").last(),
                file.asRequestBody("image/jpg".toMediaTypeOrNull())
            )
            val response = api.uploadImage(type, itemId, image)
            response.isSuccessful
        } catch (exception: Exception) {
            Log.d("ImageRepositoryImpl", "createImage exception: $exception")
            false
        }
    }
}
