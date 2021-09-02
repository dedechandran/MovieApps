package com.dedechandran.core.domain

import com.dedechandran.core.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {
    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieRepository.getFavoriteMovie().flowOn(Dispatchers.IO)
    }

    override fun getPopularMovie(): Flow<List<Movie>> {
        return movieRepository.getPopularMovie().flowOn(Dispatchers.IO)
    }

    override fun getMovieGenre(): Flow<List<Genre>> {
        return movieRepository.getMovieGenres().flowOn(Dispatchers.IO)
    }

    override fun getMovieDetails(movieId: Int): Flow<Movie> {
        return movieRepository.getMovieDetails(movieId = movieId).flowOn(Dispatchers.IO)
    }

    override fun updateFavoriteMovieState(movieId: Int, isFavorite: Boolean): Flow<Unit> {
        return movieRepository.updateMovieFavoriteState(movieId = movieId, isFavorite = isFavorite)
            .flowOn(Dispatchers.IO)
    }
}