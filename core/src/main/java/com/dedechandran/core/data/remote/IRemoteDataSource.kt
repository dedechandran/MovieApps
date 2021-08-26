package com.dedechandran.core.data.remote

import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse

interface IRemoteDataSource {
    suspend fun getPopularMovie(): PopularMovieResponse
    suspend fun getMovieGenres(): GenreResponse
}