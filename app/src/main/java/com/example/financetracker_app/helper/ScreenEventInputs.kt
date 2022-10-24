package com.example.financetracker_app.helper

import androidx.annotation.StringRes

data class InputData<out T>(
    val item: T? = null,
    @StringRes val errorId: Int? = null
)
