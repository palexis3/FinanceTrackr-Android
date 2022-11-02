package com.example.financetracker_app.ui.composable.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.financetracker_app.helper.InputData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> EmittableDropDownMenu(
    modifier: Modifier = Modifier,
    inputData: InputData<T>,
    label: String,
    listOfOptions: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        Row {
            // Note: Place this textfield into a row because the `weight` attribute
            // used in the modifier requires the scope of Column or Row
            TextField(
                modifier = modifier.weight(1f),
                value = inputData.item?.toString() ?: "",
                onValueChange = {},
                label = { Text(label) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOfOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                ) {
                    Text(text = option)
                }
            }
        }
    }
}
