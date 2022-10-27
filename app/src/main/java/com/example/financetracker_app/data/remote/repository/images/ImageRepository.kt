package com.example.financetracker_app.data.remote.repository.images

import java.io.File

interface ImageRepository {
    suspend fun createImage(type: String, itemId: String, file: File): Boolean
}
