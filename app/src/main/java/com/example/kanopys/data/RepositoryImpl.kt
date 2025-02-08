package com.example.kanopys.data

import com.example.kanopys.domain.entity.User
import com.example.kanopys.domain.repository.UserRepository

class RepositoryImpl private constructor(): UserRepository {

    override suspend fun registerAProfile(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun logInToYourProfile(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun logOutOfYourProfile(user: User) {
        TODO("Not yet implemented")
    }

    companion object {
        private val lock = Any()
        private var instance: RepositoryImpl? = null

        fun getInstance(): RepositoryImpl {
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }

                return RepositoryImpl().also { instance = it }
            }
        }
    }
}