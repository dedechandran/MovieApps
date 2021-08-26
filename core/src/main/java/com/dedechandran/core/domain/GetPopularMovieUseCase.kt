package com.dedechandran.core.domain

import com.dedechandran.core.data.MovieRepository
import com.dedechandran.core.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getPopularMovie(): Flow<List<PopularMovie>> {
        return movieRepository.getPopularMovie().flowOn(Dispatchers.IO)
    }
}