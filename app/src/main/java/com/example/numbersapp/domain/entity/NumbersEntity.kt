package com.example.numbersapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.numbersapp.domain.model.Number

@Entity(tableName = "table_numbers")
class NumbersEntity(
    var numbers: Number
){
    @PrimaryKey(autoGenerate = true) var id =0
}