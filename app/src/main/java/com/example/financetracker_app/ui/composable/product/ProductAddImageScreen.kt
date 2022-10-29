package com.example.financetracker_app.ui.composable.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker_app.R
import com.example.financetracker_app.helper.ScreenEvent
import com.example.financetracker_app.ui.composable.image.ImagePicker
import com.example.financetracker_app.ui.viewmodel.image.ImagesViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductAddImageScreen(
    closeScreen: () -> Unit,
    showSnackbar: (String, String) -> Unit,
    productId: String,
    imageViewModel: ImagesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val imageUploadScreenEvent by imageViewModel.uploadImageScreenEvent.collectAsStateWithLifecycle()

    LaunchedEffect(imageUploadScreenEvent) {
        when (val screenEvent = imageUploadScreenEvent.screenEvent) {
            is ScreenEvent.ShowSnackbar -> {
                val message = context.getString(screenEvent.stringId)
                val actionLabel = context.getString(R.string.ok)
                showSnackbar(message, actionLabel)
            }
            ScreenEvent.CloseScreen -> closeScreen.invoke()
            else -> {}
        }
    }

    Column(
        Modifier.padding(12.dp)
    ) {
        IconButton(onClick = closeScreen) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Spacer(Modifier.height(4.dp))

        ImagePicker(
            fileSelected = { file ->
                imageViewModel.uploadImage(ImagesViewModel.PRODUCT_TYPE, productId, file)
            },
            onCloseScreen = closeScreen
        )
    }
}
