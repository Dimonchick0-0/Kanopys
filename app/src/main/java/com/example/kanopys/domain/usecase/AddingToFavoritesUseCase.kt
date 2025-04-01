package com.example.kanopys.domain.usecase

import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.repository.UserRepository
import javax.inject.Inject

class AddingToFavoritesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(item: Movie) {
        repository.addingToFavorites(item)
    }
}