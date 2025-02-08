package com.example.kanopys.domain.usecase

import com.example.kanopys.domain.entity.User
import com.example.kanopys.domain.repository.UserRepository

class LogInProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.logInToYourProfile(user)
    }
}