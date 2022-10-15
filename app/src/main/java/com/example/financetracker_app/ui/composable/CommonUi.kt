package com.example.financetracker_app.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseHomeRow(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    CommonDivider()
    Row(
        modifier = modifier
            .height(64.dp)
            .clickable { onClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
        )
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "next icon",
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp)
        )
    }
}

@Composable
fun CommonDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp, modifier = modifier)
}
