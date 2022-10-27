package com.example.financetracker_app.helper

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.financetracker_app.R
import java.io.File

data class FileObjects(
    val uri: Uri,
    val file: File
)

class ComposeFileProvider : FileProvider(
    R.xml.file_paths
) {
    companion object {
        fun getFileObjects(context: Context): FileObjects {
            val imagePath = File(context.getExternalFilesDir(null), "my_images")
            imagePath.mkdirs()

            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                imagePath
            )

            val authority = context.packageName + ".fileprovider"

            val uri = getUriForFile(
                context,
                authority,
                file
            )

            return FileObjects(uri = uri, file = file)
        }
    }
}
