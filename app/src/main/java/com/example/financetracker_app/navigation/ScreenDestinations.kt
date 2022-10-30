package com.example.financetracker_app.navigation

/**
 * RootScreenDestination is used to identify routes that lead to a nested navigation graph
 * and doesn't represent an individual screen a user will see.
 *
 * LeafScreenDestination represents individual screens that exist under a nested navigation graph.
 */

interface LeafScreenDestination {
    val route: String
}

interface RootScreenDestination {
    val route: String
}
