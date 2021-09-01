package com.dedechandran.core.domain

import com.dedechandran.core.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMovieDetails @Inject constructor(
    private val movieRepository: MovieRepository
) {

    fun getPopularMovieDetails(movieId: Int): Flow<PopularMovie> {
        return movieRepository.getFavoriteMovieDetails(movieId = movieId).flowOn(Dispatchers.IO)
    }
}