package com.example.financetracker_app.helper

import androidx.annotation.StringRes

sealed interface ScreenEvent<out T> {
    data class ShowSnackbar(@StringRes val stringId: Int) : ScreenEvent<Nothing>
    data class ScreenEventWithContent<T>(val item: T) : ScreenEvent<T>
    object CloseScreen : ScreenEvent<Nothing>
    data class GoToNextScreen(val arg: String) : ScreenEvent<Nothing>
}
