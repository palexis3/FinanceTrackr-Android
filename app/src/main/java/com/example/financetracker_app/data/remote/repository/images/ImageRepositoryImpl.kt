package com.example.financetracker_app.data.remote.repository.images

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val api: FinanceTrackrApi
) : ImageRepository {

    override suspend fun createImage(type: String, itemId: String, imageUri: Uri): Boolean {
//        val file = Uri.fromFile(
//            context.contentResolver.getType(imageUri)?.let {
//                File(
//                    context.cacheDir,
//                    it
//                )
//            }
//        ).toFile()

        return try {
            val file = File(imageUri.path)
            val image = MultipartBody.Part.create(
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
            Log.d("ImageRepositoryImpl", "file: $file")
            Log.d("ImageRepositoryImpl", "image: $image")
            val response = api.uploadImage(type, itemId, image)
            response.isSuccessful
        } catch (exception: Exception) {
            Log.d("ImageRepositoryImpl", "createImage exception: $exception")
            false
        }
    }
}
