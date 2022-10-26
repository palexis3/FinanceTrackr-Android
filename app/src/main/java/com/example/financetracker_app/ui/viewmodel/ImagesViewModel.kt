package com.example.financetracker_app.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.R
import com.example.financetracker_app.data.remote.repository.images.ImageRepository
import com.example.financetracker_app.helper.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ImageUploadScreenEventWrapper(
    val screenEvent: ScreenEvent<Nothing>? = null
)

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _uploadImageScreenEvent = MutableStateFlow(ImageUploadScreenEventWrapper())
    val uploadImageScreenEvent = _uploadImageScreenEvent.asStateFlow()

    fun uploadImage(type: String, itemId: String, imageUri: Uri?) {
        viewModelScope.launch {
            val wasUploadSuccessful = imageUri?.let { uri ->
                imageRepository.createImage(type, itemId, uri)
            } ?: false

            Log.d("ImageViewModel", "wasUploadSuccessful: $wasUploadSuccessful")

            val screenEvent = if (wasUploadSuccessful) {
                ScreenEvent.CloseScreen
            } else {
                ScreenEvent.ShowSnackbar(stringId = R.string.image_upload_error)
            }
            _uploadImageScreenEvent.update { screenEventWrapper -> screenEventWrapper.copy(screenEvent = screenEvent) }
        }
    }

    companion object {
        val PRODUCT_TYPE = "product"
        val RECEIPT_TYPE = "receipt"
    }
}
