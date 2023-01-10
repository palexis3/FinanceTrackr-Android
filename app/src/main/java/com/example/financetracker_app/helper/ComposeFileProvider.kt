package com.example.financetracker_app.helper

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.financetracker_app.BuildConfig
import com.example.financetracker_app.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

data class FileObjects(
    val uri: Uri,
    val file: File
)

class ComposeFileProvider : FileProvider(
    R.xml.file_paths
) {
    companion object {
        fun getFileObjects(context: Context): FileObjects {
            val file = context.createImageFile()

            val uri = getUriForFile(
                Objects.requireNonNull(context),
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )

            return FileObjects(uri = uri, file = file)
        }
    }
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"

    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}
