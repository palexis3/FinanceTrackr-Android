package com.example.financetracker_app.data.remote.repository.images

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val api: FinanceTrackrApi
): ImageRepository {

    override suspend fun createImage(type: String, id: String, imageUri: Uri): Boolean {
        val file = Uri.fromFile(
            context.contentResolver.getType(imageUri)?.let {
                File(
                    context.cacheDir,
                    it
                )
            }
        ).toFile()

        val image = MultipartBody.Part.create(
            RequestBody.create(MediaType.parse("image/*"), file)
        )
        val response = api.uploadImage(type, id, image)
        return response.isSuccessful
    }
}