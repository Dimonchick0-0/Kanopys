package com.example.kanopys.data.repository

import androidx.lifecycle.LiveData
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

    override suspend fun authenticationUser(emailUser: String, passwordUser: String): User {
        val user = localDataSourceImpl.authUser(emailUser, passwordUser)
        return user
    }
}