package com.dedechandran.core.domain

import com.dedechandran.core.data.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    fun getFavoriteMovie(): Flow<List<Movie>> {
        return movieRepository.getFavoriteMovie()
    }
}