package com.example.wishlistapp

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen_route")
    object AddScreen: Screen("add_screen")
}