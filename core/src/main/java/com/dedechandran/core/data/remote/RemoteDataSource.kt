package com.dedechandran.core.data.remote

import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : IRemoteDataSource {

    override suspend fun getPopularMovie(): Flow<ApiResponse<PopularMovieResponse>> {
        return flow {
                val result = apiService.getMoviePopularList()
                if (result.results.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(data = result))
                }
        }.catch { t ->
            emit(ApiResponse.Error( t.message ?: "There is something wrong"))
        }
    }
}