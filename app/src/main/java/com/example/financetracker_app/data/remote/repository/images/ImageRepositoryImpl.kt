package com.example.financetracker_app.data.remote.repository.images

import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val api: FinanceTrackrApi
): ImageRepository {

    override fun createImage(type: String, id: String, imageUri: Uri?): Response<Unit> {
        TODO("Not yet implemented")
    }
}