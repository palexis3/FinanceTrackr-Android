package com.example.financetracker_app.ui.composable.image

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.financetracker_app.R
import com.example.financetracker_app.helper.ComposeFileProvider

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    uriSelected: (Uri) -> Unit,
    onCloseScreen: () -> Unit
) {
    val context = LocalContext.current
    var imageExists by rememberSaveable { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageExists = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            imageExists = success
        }
    )

    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onCloseScreen) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close Screen")
            }
            Button(
                onClick = {
                    imageUri?.let { uriSelected(it) }
                    onCloseScreen.invoke()
                },
                enabled = imageExists
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm Image")
            }
        }
        Box {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageExists && imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = stringResource(id = R.string.selected_image)
                    )
                }
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { imagePicker.launch("image/*") }
                ) {
                    Text(stringResource(id = R.string.select_image))
                }
                Button(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        val uri = ComposeFileProvider.getImageUri(context)
                        Log.d("ImagePicker", "image uri: $uri")
                        imageUri = uri
                        cameraLauncher.launch(uri)
                    }
                ) {
                    Text(stringResource(id = R.string.take_photo))
                }
            }
        }
    }
}
