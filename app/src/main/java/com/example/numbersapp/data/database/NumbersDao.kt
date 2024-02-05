package com.example.numbersapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.numbersapp.domain.entity.NumbersEntity


@Dao
interface NumbersDao {

    @Query("SELECT * FROM table_numbers ORDER BY id ASC")
    fun getAllData(): LiveData<List<NumbersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNumber(numbersEntity: NumbersEntity)


    @Query("DELETE FROM table_numbers")
    fun deleteAll()
}