package com.example.numbersapp.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    /** * ROOM * **/
    val readNumbers: LiveData<List<NumbersEntity>> = repository.local.getAllData

    private fun insertNumber(numbersEntity: NumbersEntity) {
        viewModelScope.launch {
            repository.local.insertNumber(numbersEntity)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAll()
        }
    }

    /** * Retrofit * **/
    val myResponse: MutableLiveData<Response<Number>> = MutableLiveData()

    fun getRandomNumber() {
        viewModelScope.launch {
            getRandomNumberSafeCall()
        }
    }

    fun getInputNumber(inputNumber: Int) {
        viewModelScope.launch {
            getInputNumberSafeCall(inputNumber)
        }
    }

    private suspend fun getRandomNumberSafeCall() {
        myResponse.value = Response.Loading()
        if (true) {
            val response = repository.remote.getRandomNumber()
            myResponse.value = handleNumberResponse(response)

            val number = myResponse.value!!.data
            if (number != null) {
                offlineCache(number)
            }

        } else {
            myResponse.value = Response.Error("No Internet Connection")
        }
    }

    private suspend fun getInputNumberSafeCall(inputNumber: Int) {
        myResponse.value = Response.Loading()
        if (true) {
            val response = repository.remote.getInputNumber(inputNumber)
            myResponse.value = handleNumberResponse(response)

            val number = myResponse.value!!.data
            if (number != null) {
                offlineCache(number)
            }
        } else {
            myResponse.value = Response.Error("No Internet Connection")
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

    private fun offlineCache(number: Number) {
        val numbersEntity = NumbersEntity(number)
        insertNumber(numbersEntity)
    }

    /** Internet **/
    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}