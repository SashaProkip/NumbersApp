package com.example.numbersapp.data.database

import androidx.lifecycle.LiveData
import com.example.numbersapp.domain.entity.NumbersEntity
import javax.inject.Inject


class LocalDataSource @Inject constructor(private val numbersDao: NumbersDao) {

    val getAllData: List<NumbersEntity> = numbersDao.getAllData()

    fun insertNumber(numbersEntity: NumbersEntity) {
        numbersDao.insertNumber(numbersEntity)
    }

    fun deleteAll() {
        numbersDao.deleteAll()
    }

}