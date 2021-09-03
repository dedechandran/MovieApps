package com.dedechandran.core.data

import com.dedechandran.core.domain.movie.model.Genre
import com.dedechandran.core.domain.movie.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovie(): Flow<List<Movie>>
    fun getMovieGenres(): Flow<List<Genre>>
    fun updateMovieFavoriteState(movieId: Int, isFavorite: Boolean): Flow<Unit>
    fun getMovieDetails(movieId: Int): Flow<Movie>
    fun getFavoriteMovie(): Flow<List<Movie>>
}