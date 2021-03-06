package com.dedechandran.core.domain.movie

import com.dedechandran.core.data.IMovieRepository
import com.dedechandran.core.domain.movie.model.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieGenreUseCase @Inject constructor(private val movieRepository: IMovieRepository) {

    fun getMovieGenre(): Flow<List<Genre>> {
        return movieRepository.getMovieGenres().flowOn(Dispatchers.IO)
    }
}