package com.example.financetracker_app.navigation.home

import com.example.financetracker_app.navigation.LeafScreenDestination
import com.example.financetracker_app.navigation.RootScreenDestination

object HomeRoot : RootScreenDestination {
    override val route = "homeRoot"
    override val title = "Home"
}

object Home : LeafScreenDestination {
    override val route = "home"
    override val title = "Home"
}
