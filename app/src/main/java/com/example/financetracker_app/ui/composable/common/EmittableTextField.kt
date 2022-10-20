package com.example.financetracker_app.ui.composable.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.financetracker_app.ui.viewmodel.product.InputData

@Composable
fun EmittableTextField(
    modifier: Modifier = Modifier,
    inputData: InputData,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column {
        TextField(
            modifier = modifier,
            value = inputData.item,
            onValueChange = { onValueChange(it) },
            isError = inputData.errorId != null,
            label = { Text(label) },
            keyboardOptions = keyboardOptions
        )
        inputData.errorId?.let {
            Text(text = stringResource(id = it))
        }
    }
}
