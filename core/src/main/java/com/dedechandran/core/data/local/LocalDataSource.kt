package com.dedechandran.core.data.local

import com.dedechandran.core.data.local.popularmovie.MovieDao
import com.dedechandran.core.data.local.popularmovie.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) : ILocalDataSource{

    override fun getMovies(type: String): Flow<List<MovieEntity>> {
        return movieDao.getAllMovies(type = type)
    }

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies = movies)
    }

    override suspend fun updateMovieFavoriteState(movieId: Int, isFavorite: Boolean) {
        movieDao.updateMovieFavoriteState(id = movieId, isFavorite = isFavorite)
    }

    override suspend fun updateMovieDetails(
        id: Int,
        revenue: Long?,
        runtime: Int?,
        voteAverage: Double?
    ) {
        movieDao.updateMovieDetails(id = id, revenue = revenue, runtime = runtime, voteAverage = voteAverage)
    }

    override fun getMovieById(movieId: Int): Flow<MovieEntity> {
        return movieDao.getMovieById(movieId = movieId)
    }

}