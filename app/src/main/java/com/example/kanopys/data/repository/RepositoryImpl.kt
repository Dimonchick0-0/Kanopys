package com.example.kanopys.data.repository

import com.example.kanopys.data.localdatasoure.LocalDataSourceImpl
import com.example.kanopys.domain.entity.User
import com.example.kanopys.domain.repository.UserRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSourceImpl: LocalDataSourceImpl
) : UserRepository {

    suspend fun checkForEmail(emailUser: String): Boolean {
        return localDataSourceImpl.checkForEmail(emailUser)
    }

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