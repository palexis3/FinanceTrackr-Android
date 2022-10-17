package com.example.financetracker_app.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BaseHomeRow(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(64.dp)
            .clickable { onClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = title),
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
fun ScreenTitle(
    modifier: Modifier = Modifier,
    @StringRes title: Int
) {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.h3,
        modifier = modifier.padding(4.dp)
    )
}

@Composable
fun ErrorTitle(
    modifier: Modifier = Modifier,
    @StringRes title: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LoadingIcon(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CommonDivider(modifier: Modifier = Modifier) {
    Divider(modifier = modifier.height(2.dp))
}
