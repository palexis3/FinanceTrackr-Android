package com.example.financetracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.financetracker_app.ui.composable.HomeScreen
import com.example.financetracker_app.ui.theme.FinanceTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceTrackerAppTheme(darkTheme = true) {
               HomeScreen()
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    FinanceTrackerAppTheme {
        HomeScreen()
    }
}
