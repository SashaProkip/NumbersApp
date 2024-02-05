package com.example.numbersapp.di

import android.content.Context
import androidx.room.Room
import com.example.numbersapp.data.database.NumbersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNumbersDao(numbersDatabase: NumbersDatabase) = numbersDatabase.numbersDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NumbersDatabase::class.java,
        "table_numbers"
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}