package com.example.kanopys.domain.usecase

import com.example.kanopys.domain.repository.UserRepository
import javax.inject.Inject

class DeleteMovieFromFavoriteUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteFromFavorite(id)
    }
}