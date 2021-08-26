package com.dedechandran.core.domain

import com.dedechandran.core.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdatePopularMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    fun updatePopularMovie(popularMovie: PopularMovie): Flow<Unit> {
        return movieRepository.updatePopularMovie(popularMovie).flowOn(Dispatchers.IO)
    }
}