package com.example.financetracker_app.ui.composable.common

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.financetracker_app.ui.viewmodel.product.InputData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmittableDropDownMenu(
    modifier: Modifier = Modifier,
    inputData: InputData,
    label: String,
    listOfOptions: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(listOfOptions[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = modifier,
            value = inputData.item,
            onValueChange = {
                onValueChange(selectedOptionText)
            },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOfOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = option
                        expanded = false
                    }
                ) {
                    Text(text = option)
                }
            }
        }
    }
}
