package com.example.kanopys.domain.usecase

import com.example.kanopys.domain.entity.User
import com.example.kanopys.domain.repository.UserRepository
import javax.inject.Inject

class RegisterAProfileUseCase @Inject constructor (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.registerAProfile(user)
    }
}