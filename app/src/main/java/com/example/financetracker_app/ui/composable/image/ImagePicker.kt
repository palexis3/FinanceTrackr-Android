package com.example.financetracker_app.ui.composable.image

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.financetracker_app.R
import com.example.financetracker_app.helper.ComposeFileProvider
import com.example.financetracker_app.helper.imageUriToFile
import java.io.File

/**
 * ImagePicker is created to be nested in another composable screen to allow the image file
 * that a user has selected whether it be taken or chosen from the gallery to be used for
 * whatever reason.
 */
@Composable
fun ImagePicker(
    fileSelected: (File) -> Unit,
    onCloseScreen: () -> Unit
) {
    val context = LocalContext.current
    var imageExists by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    var imageFile by remember { mutableStateOf<File?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            imageExists = success
        }
    )

    val permissionCheckResult = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { success ->
            if (success) {
                cameraLauncherHelper(context, cameraLauncher) { uri, file ->
                    imageUri = uri
                    imageFile = file
                }
            } else {
                Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_SHORT).show()
            }
        }
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { imagePickedUri ->
            imageExists = imagePickedUri != null
            imageUri = imagePickedUri
            imageLauncherHelper(
                context,
                imagePickedUri
            ) { file ->
                if (file != null) {
                    imageFile = file
                }
            }
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onCloseScreen) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_screen)
                )
            }
            if (imageExists) {
                Button(
                    onClick = {
                        imageFile?.let { fileSelected(it) }
                        // reset mutable values to enable user to re-take a photo in case
                        // there was an exception
                        imageExists = false
                        imageUri = null
                    }
                ) {
                    Text(stringResource(id = R.string.upload))
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.confirm_image)
                    )
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
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncherHelper(context, cameraLauncher) { uri, file ->
                            imageUri = uri
                            imageFile = file
                        }
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            ) {
                Text(stringResource(id = R.string.take_photo))
            }
        }
    }
}

fun imageLauncherHelper(
    context: Context,
    imageUri: Uri?,
    fileCreated: (File?) -> Unit
) {
    val file = imageUri?.let { context.imageUriToFile(it) }
    fileCreated(file)
}

fun cameraLauncherHelper(
    context: Context,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    uriAndFile: (Uri, File) -> Unit
) {
    val fileObject = ComposeFileProvider.getFileObjects(context)
    uriAndFile(fileObject.uri, fileObject.file)
    cameraLauncher.launch(fileObject.uri)
}
