package com.dedechandran.core.data.local

import com.dedechandran.core.data.local.popularmovie.PopularMovieDao
import com.dedechandran.core.data.local.popularmovie.PopularMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val popularMovieDao: PopularMovieDao
) : ILocalDataSource{

    override fun getPopularMovie(): Flow<List<PopularMovieEntity>> {
        return popularMovieDao.getAllPopularMovies()
    }

    override suspend fun insertPopularMovies(popularMovies: List<PopularMovieEntity>) {
        popularMovieDao.insertPopularMovies(popularMovies = popularMovies)
    }

}