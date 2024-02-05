package com.example.numbersapp.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numbersapp.data.repo.Repository
import com.example.numbersapp.domain.entity.NumbersEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.numbersapp.data.util.Response
import com.example.numbersapp.domain.model.Number
import com.example.numbersapp.ui.state.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState(
        numbersList = repository.local.getAllData.toMutableList()
    ))
    val mainState = _mainState.asStateFlow()

    private fun insertNumber(numbersEntity: NumbersEntity) {
        viewModelScope.launch {
            repository.local.insertNumber(numbersEntity)
        }
        _mainState.update { state ->
            val newList = listOf(numbersEntity) + state.numbersList
            state.copy(
                numbersList = newList
            )
        }
    }

    val myResponse: MutableLiveData<Response<Number>> = MutableLiveData()

    fun getRandomNumber() {
        viewModelScope.launch {
            myResponse.value = Response.Loading()
            val response = repository.remote.getRandomNumber()
            myResponse.value = handleNumberResponse(response)

            val number = myResponse.value!!.data
            if (number != null) {
                val numbersEntity = NumbersEntity(number)
                insertNumber(numbersEntity)
            }
        }
    }

    fun getInputNumber(inputNumber: Int) {
        viewModelScope.launch {
            myResponse.value = Response.Loading()
            val response = repository.remote.getInputNumber(inputNumber)
            myResponse.value = handleNumberResponse(response)

            val number = myResponse.value!!.data
            if (number != null) {
                val numbersEntity = NumbersEntity(number)
                insertNumber(numbersEntity)
            }
        }
    }

    private fun handleNumberResponse(response: retrofit2.Response<com.example.numbersapp.domain.model.Number>): Response<com.example.numbersapp.domain.model.Number> {
        return when {
            response.message().toString().contains("timeout") -> {
                Response.Error("Timeout")
            }
            response.body()!!.text.isEmpty() -> {
                Response.Error("Input new number")
            }
            response.isSuccessful -> {
                val number = response.body()
                Response.Success(number)
            }
            else -> {
                Response.Error(response.message())
            }
        }
    }
}