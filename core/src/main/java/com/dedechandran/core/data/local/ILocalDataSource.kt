package com.dedechandran.core.data.local

import com.dedechandran.core.data.local.popularmovie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getMovies(type: String): Flow<List<MovieEntity>>
    suspend fun insertMovies(movies: List<MovieEntity>)
    suspend fun updateMovieFavoriteState(movieId: Int, isFavorite: Boolean)
    suspend fun updateMovieDetails(
        id: Int,
        status: String?,
        runtime: Int?,
        voteAverage: Double?
    )
    fun getMovieById(movieId: Int): Flow<MovieEntity>
}