package com.dedechandran.core.data.remote

import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : IRemoteDataSource {

    override suspend fun getPopularMovie():PopularMovieResponse {
        return apiService.getMoviePopularList()
    }

    override suspend fun getMovieGenres(): GenreResponse {
        return apiService.getGenres("movie")
    }
}