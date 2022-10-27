package com.example.financetracker_app.ui.composable.image

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
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
    modifier: Modifier = Modifier,
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

    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.End) {
            Button(onClick = onCloseScreen) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close Screen")
            }
            if (imageExists) {
                Spacer(Modifier.width(4.dp))
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
            modifier = Modifier
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
            Text(stringResource(id = R.string.or))
            Button(
                modifier = Modifier.padding(top = 8.dp),
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
