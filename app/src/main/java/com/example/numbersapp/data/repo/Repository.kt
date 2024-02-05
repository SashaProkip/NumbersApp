package com.example.numbersapp.data.repo

import com.example.numbersapp.data.database.LocalDataSource
import com.example.numbersapp.data.network.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) {
    val remote = remoteDataSource
    val local = localDataSource
}