package com.example.kanopys.data.mapping

import com.example.kanopys.data.database.UserDatabase
import com.example.kanopys.domain.entity.User
import javax.inject.Inject

class MappingUser @Inject constructor() {
    fun mapEntityUserToDatabaseUser(user: User): UserDatabase {
        return UserDatabase(
            id = user.id,
            displayName = user.displayName,
            password = user.password,
            email = user.email
        )
    }
}