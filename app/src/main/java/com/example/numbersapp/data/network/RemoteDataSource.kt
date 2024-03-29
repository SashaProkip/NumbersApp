package com.example.numbersapp.data.network

import com.example.numbersapp.domain.model.Number
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val numbersApi: NumbersApi) {

    suspend fun getRandomNumber(): Response<Number> {
        return numbersApi.getRandomNumber()
    }

    suspend fun getInputNumber(inputNumber: Int): Response<Number> {
        return numbersApi.getInputNumber(inputNumber)
    }
}