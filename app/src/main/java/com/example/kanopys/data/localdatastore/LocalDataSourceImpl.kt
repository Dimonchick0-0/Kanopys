package com.example.kanopys.data.localdatastore

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
}