package com.dedechandran.core.domain

import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getPopularMovie(): Flow<List<Movie>>
    fun getMovieGenre(): Flow<List<Genre>>
    fun getMovieDetails(movieId: Int): Flow<Movie>
    fun updateFavoriteMovieState(movieId: Int, isFavorite: Boolean): Flow<Unit>
}