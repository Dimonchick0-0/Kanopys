package com.example.kanopys.data.repository

import com.example.kanopys.data.localdatastore.LocalDataSourceImpl
import com.example.kanopys.domain.entity.User
import com.example.kanopys.domain.repository.UserRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : UserRepository {

    @Inject
    lateinit var localDataSourceImpl: LocalDataSourceImpl

    override suspend fun registerAProfile(user: User) {
        localDataSourceImpl.registerUser(user)
    }

    override suspend fun logInToYourProfile(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun logOutOfYourProfile(user: User) {
        TODO("Not yet implemented")
    }
}