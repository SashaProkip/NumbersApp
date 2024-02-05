package com.example.numbersapp.ui.navigation

sealed class Screens(val route:String) {
    object FirstScreen: Screens("first_screen")
    object SecondScreen: Screens("second_screen")
}