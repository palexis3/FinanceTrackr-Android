package com.example.financetracker_app.ui.composable.product

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.helper.ScreenEvent
import com.example.financetracker_app.ui.composable.image.ImagePicker
import com.example.financetracker_app.ui.viewmodel.ImagesViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductAddImageScreen(
    closeScreen: () -> Unit,
    showSnackbar: (String, String) -> Unit,
    productId: String,
    imageViewModel: ImagesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val imageUploadScreeEvent by imageViewModel.uploadImageScreenEvent.collectAsStateWithLifecycle()

    LaunchedEffect(imageUploadScreeEvent) {
        when (val screenEvent = imageUploadScreeEvent.screenEvent) {
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            ScreenEvent.CloseScreen -> closeScreen.invoke()
            else -> {}
        }
    }

    ImagePicker(
        uriSelected = { uri ->
            Log.d("ProductAddImageScreen", "uri: $uri")
//            imageViewModel.uploadImage(ImagesViewModel.PRODUCT_TYPE, productId, uri)
        },
        onCloseScreen = closeScreen
    )
}
