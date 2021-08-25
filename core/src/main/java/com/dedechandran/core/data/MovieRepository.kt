package com.dedechandran.core.data

import com.dedechandran.core.data.local.LocalDataSource
import com.dedechandran.core.data.local.popularmovie.toDomain
import com.dedechandran.core.data.remote.ApiResponse
import com.dedechandran.core.data.remote.RemoteDataSource
import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import com.dedechandran.core.data.remote.popularmovie.toEntity
import com.dedechandran.core.domain.PopularMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getPopularMovie(): Flow<Resource<List<PopularMovie>>> {
        return object : NetworkBoundResource<List<PopularMovie>, PopularMovieResponse>() {
            override fun loadFromDb(): Flow<List<PopularMovie>> {
                return localDataSource.getPopularMovie().map {
                    it.toDomain()
                }
            }

            override fun shouldFetch(data: List<PopularMovie>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<PopularMovieResponse>> {
                return remoteDataSource.getPopularMovie()
            }

            override suspend fun saveCallResult(data: PopularMovieResponse) {
                localDataSource.insertPopularMovies(data.toEntity())
            }

        }.asFlow()
    }
}