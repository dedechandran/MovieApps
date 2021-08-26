package com.dedechandran.core.data.local

import com.dedechandran.core.data.local.popularmovie.PopularMovieDao
import com.dedechandran.core.data.local.popularmovie.PopularMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val popularMovieDao: PopularMovieDao
) : ILocalDataSource{

    override fun getPopularMovies(): Flow<List<PopularMovieEntity>> {
        return popularMovieDao.getAllMovies()
    }

    override suspend fun insertPopularMovies(popularMovies: List<PopularMovieEntity>) {
        popularMovieDao.insertMovies(popularMovies = popularMovies)
    }

    override suspend fun updatePopularMovie(popularMovie: PopularMovieEntity) {
        popularMovieDao.updateMovie(popularMovie = popularMovie)
    }

}