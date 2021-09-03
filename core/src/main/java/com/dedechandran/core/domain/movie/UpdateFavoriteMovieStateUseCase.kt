package com.dedechandran.core.domain.movie

import com.dedechandran.core.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateFavoriteMovieStateUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    fun updateFavoriteMovieState(movieId: Int, isFavorite: Boolean): Flow<Unit> {
        return movieRepository.updateMovieFavoriteState(movieId = movieId, isFavorite = isFavorite)
            .flowOn(Dispatchers.IO)
    }

}