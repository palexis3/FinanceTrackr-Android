package com.example.financetracker_app.ui.composable.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.financetracker_app.helper.InputData

@Composable
fun <T> EmittableTextField(
    inputData: InputData<T>,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    Column {
        Row {
            // Note: Place this textfield into a row because the `weight` attribute
            // used in the modifier requires the scope of Column or Row
            TextField(
                modifier = modifier.weight(1f),
                value = inputData.item?.toString() ?: "",
                onValueChange = { onValueChange(it) },
                isError = inputData.errorId != null,
                label = { Text(label) },
                keyboardOptions = keyboardOptions
            )
        }
        inputData.errorId?.let {
            Text(text = stringResource(id = it))
        }
    }
}
