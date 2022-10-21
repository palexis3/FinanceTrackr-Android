package com.example.financetracker_app.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FinanceTrackrAppState(
    val navHostController: NavHostController,
    val snackbarScope: CoroutineScope,
    val scaffoldState: ScaffoldState
) {

    fun showSnackBar(message: String, actionLabel: String) {
        snackbarScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel
            )
        }
    }
}

@Composable
fun rememberFinanceTrackrAppState(
    navHostController: NavHostController = rememberNavController(),
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = remember {
            SnackbarHostState()
        }
    )
) = remember(navHostController, snackbarScope, scaffoldState) {
    FinanceTrackrAppState(navHostController, snackbarScope, scaffoldState)
}
