package com.example.financetracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.financetracker_app.navigation.BottomBar
import com.example.financetracker_app.navigation.FinanceTrackrAppState
import com.example.financetracker_app.navigation.ScreensNavigation
import com.example.financetracker_app.navigation.TopBar
import com.example.financetracker_app.navigation.rememberFinanceTrackrAppState
import com.example.financetracker_app.ui.theme.FinanceTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceTrackerAppTheme {
                ShowApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ShowApp() {
    val appState: FinanceTrackrAppState = rememberFinanceTrackrAppState()
    var title by rememberSaveable { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            if (appState.isBottomNavScreen) {
                BottomBar(
                    bottomNavScreens = appState.bottomNavScreens,
                    currentRoute = appState.currentRoute!!,
                    navigateToScreen = appState::navigateToScreen
                )
            }
        },
        topBar = {
            TopBar(
                title = title,
                isBottomNavScreen = appState.isBottomNavScreen,
                closeScreen = appState::closeScreen
            )
        }
    ) { innerPadding ->
        ScreensNavigation(
            navController = appState.navController,
            modifier = Modifier.padding(innerPadding),
            showSnackbar = { message, actionLabel ->
                appState.showSnackBar(message, actionLabel)
            },
            title = { item -> title = item }
        )
    }
}
