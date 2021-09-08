package com.dedechandran.core.domain.movie

import com.dedechandran.core.data.IMovieRepository
import com.dedechandran.core.domain.movie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val movieRepository: IMovieRepository) {

    fun getMovieDetails(movieId: Int): Flow<Movie> {
        return movieRepository.getMovieDetails(movieId).flowOn(Dispatchers.IO)
    }
}