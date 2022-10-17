package com.example.financetracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.financetracker_app.navigation.ScreenNavHost
import com.example.financetracker_app.ui.theme.FinanceTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowHomeScreen()
        }
    }
}

@Preview
@Composable
fun ShowHomeScreen() {
    FinanceTrackerAppTheme {
        val navController = rememberNavController()
        Scaffold { innerPadding ->
            ScreenNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
