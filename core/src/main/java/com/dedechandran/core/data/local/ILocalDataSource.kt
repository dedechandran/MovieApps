package com.dedechandran.core.data.local

import com.dedechandran.core.data.local.popularmovie.PopularMovieEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getPopularMovie(): Flow<List<PopularMovieEntity>>
    suspend fun insertPopularMovies(popularMovies: List<PopularMovieEntity>)
}