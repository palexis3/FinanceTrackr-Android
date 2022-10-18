package com.example.financetracker_app.helper

import androidx.annotation.StringRes

sealed interface ScreenEvent<out T> {
    data class ShowToast(@StringRes val stringId: Int) : ScreenEvent<Nothing>
    data class ScreenEventWithContent<T>(val item: T) : ScreenEvent<T>
    object CloseScreen : ScreenEvent<Nothing>
}
