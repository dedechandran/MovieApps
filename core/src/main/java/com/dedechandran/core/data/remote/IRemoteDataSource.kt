package com.dedechandran.core.data.remote

import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    suspend fun getPopularMovie(): Flow<ApiResponse<PopularMovieResponse>>
}