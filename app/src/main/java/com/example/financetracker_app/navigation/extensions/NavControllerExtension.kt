package com.example.financetracker_app.navigation.extensions

import androidx.navigation.NavHostController

/**
 * `navigateSingleTopTo` extension is used to ensure that for each screen we navigate to, there's
 * only a single copy that exists in the backstack to avoid confusing, circular navigation
 */
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
