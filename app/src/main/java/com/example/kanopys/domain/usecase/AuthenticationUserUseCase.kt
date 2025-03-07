package com.example.kanopys.domain.usecase

import com.example.kanopys.domain.entity.User
import com.example.kanopys.domain.repository.UserRepository
import javax.inject.Inject

class AuthenticationUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(emailUser: String, passwordUser: String): User {
        return repository.authenticationUser(emailUser, passwordUser)
    }
}