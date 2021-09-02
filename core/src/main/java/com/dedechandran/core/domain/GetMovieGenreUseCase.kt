package com.dedechandran.core.domain

import com.dedechandran.core.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieGenreUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    fun getMovieGenre(): Flow<List<Genre>> {
        return movieRepository.getMovieGenres().flowOn(Dispatchers.IO)
    }
}