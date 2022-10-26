package com.example.financetracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.financetracker_app.navigation.FinanceTrackrAppState
import com.example.financetracker_app.navigation.ScreensNavigation
import com.example.financetracker_app.navigation.rememberFinanceTrackrAppState
import com.example.financetracker_app.ui.theme.FinanceTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowApp()
        }
    }
}

@Preview
@Composable
fun ShowApp() {
    FinanceTrackerAppTheme {
        val appState: FinanceTrackrAppState = rememberFinanceTrackrAppState()

        Scaffold(scaffoldState = appState.scaffoldState) { innerPadding ->
            ScreensNavigation(
                navController = appState.navHostController,
                modifier = Modifier.padding(innerPadding),
                showSnackbar = { message, actionLabel ->
                    appState.showSnackBar(message, actionLabel)
                }
            )
        }
    }
}
