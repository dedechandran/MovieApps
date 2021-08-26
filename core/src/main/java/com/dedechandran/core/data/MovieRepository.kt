package com.dedechandran.core.data

import com.dedechandran.core.data.local.LocalDataSource
import com.dedechandran.core.data.local.popularmovie.toDomain
import com.dedechandran.core.data.remote.RemoteDataSource
import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import com.dedechandran.core.data.remote.popularmovie.toEntity
import com.dedechandran.core.domain.Genre
import com.dedechandran.core.domain.PopularMovie
import com.dedechandran.core.domain.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getPopularMovie(): Flow<List<PopularMovie>> {
        return object : NetworkBoundResource<List<PopularMovie>, PopularMovieResponse>() {
            override fun loadFromDb(): Flow<List<PopularMovie>> {
                return localDataSource.getPopularMovies().map {
                    it.toDomain()
                }
            }

            override fun shouldFetch(data: List<PopularMovie>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): PopularMovieResponse {
                return remoteDataSource.getPopularMovie()
            }

            override suspend fun saveCallResult(data: PopularMovieResponse) {
                localDataSource.insertPopularMovies(data.toEntity())
            }

        }.asFlow()
    }

    override fun getMovieGenres(): Flow<List<Genre>> {
        return flow {
            val result = remoteDataSource.getMovieGenres().genres.map { genre ->
                Genre(
                    id = genre.id,
                    name = genre.name
                )
            }
            emit(result)
        }
    }

    override fun updatePopularMovie(popularMovie: PopularMovie): Flow<Unit> {
        return flow {
            emit(
                localDataSource.updatePopularMovie(popularMovie = popularMovie.toEntity())
            )
        }
    }


}