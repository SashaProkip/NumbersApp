package com.example.numbersapp.ui.state

import com.example.numbersapp.domain.entity.NumbersEntity

data class MainState(
    val isLoading: Boolean = true,
    val error: String = "",
    //val numbersList: List<NumbersEntity>
)
