package com.example.kanopys.data.localdatasoure

import com.example.kanopys.data.database.ProfileUserDao
import com.example.kanopys.data.mapping.MappingUser
import com.example.kanopys.domain.entity.User
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val mapper: MappingUser,
    private val profileUserDao: ProfileUserDao
): LocalDataSource {
    override suspend fun registerUser(user: User) {
        profileUserDao.registerUser(mapper.mapEntityUserToDatabaseUser(user))
    }

    override suspend fun checkForEmail(emailUser: String): Boolean {
        return profileUserDao.checkForEmail(emailUser)
    }

    override suspend fun authUser(emailUser: String, passwordUser: String): User {
        return profileUserDao.authUser(emailUser, passwordUser)
    }
}