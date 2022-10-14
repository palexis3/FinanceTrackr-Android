package com.example.financetracker_app.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker_app.data.remote.repository.images.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _uploadImageFlow = MutableSharedFlow<Boolean>()
    val uploadImageFlow = _uploadImageFlow.asSharedFlow()

    fun uploadImage(type: String, itemId: String, imageUri: Uri?) {
        viewModelScope.launch {
            val response = imageUri?.let { uri ->
                imageRepository.createImage(type, itemId, uri)
            } ?: false

            _uploadImageFlow.emit(response)
        }
    }
}
