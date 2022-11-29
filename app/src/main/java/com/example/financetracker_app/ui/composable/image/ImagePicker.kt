package com.example.financetracker_app.ui.composable.image

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.financetracker_app.R
import com.example.financetracker_app.helper.ComposeFileProvider
import java.io.File

@Composable
fun ImagePicker(
    fileSelected: (File) -> Unit,
    onCloseScreen: () -> Unit
) {
    val context = LocalContext.current
    var imageExists by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var file by remember { mutableStateOf<File?>(null) }

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

    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onCloseScreen) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close Screen")
            }
            if (imageExists) {
                Button(
                    onClick = {
                        file?.let { fileSelected(it) }
                        // reset mutable values to enable user to re-take a photo in case
                        // there was an exception
                        imageExists = false
                        imageUri = null
                    }
                ) {
                    Text(stringResource(id = R.string.upload))
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm Image")
                }
            }
        }
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageExists && imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = stringResource(id = R.string.selected_image)
                )
                Spacer(Modifier.height(16.dp))
            }
            Button(
                onClick = { imagePicker.launch("image/*") }
            ) {
                Text(stringResource(id = R.string.select_image))
            }

            Spacer(Modifier.height(4.dp))
            Text(stringResource(id = R.string.or))
            Spacer(Modifier.height(4.dp))

            Button(
                onClick = {
                    val fileObject = ComposeFileProvider.getFileObjects(context)
                    imageUri = fileObject.uri
                    file = fileObject.file
                    cameraLauncher.launch(fileObject.uri)
                }
            ) {
                Text(stringResource(id = R.string.take_photo))
            }
        }
    }
}
