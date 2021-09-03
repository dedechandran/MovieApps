package com.dedechandran.core.data

import com.dedechandran.core.data.local.LocalDataSource
import com.dedechandran.core.data.local.popularmovie.toMovieDomain
import com.dedechandran.core.data.remote.RemoteDataSource
import com.dedechandran.core.data.remote.details.MovieDetailsResponse
import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import com.dedechandran.core.data.remote.popularmovie.toEntity
import com.dedechandran.core.domain.movie.model.Genre
import com.dedechandran.core.domain.movie.model.MovieType
import com.dedechandran.core.domain.movie.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getPopularMovie(): Flow<List<Movie>> {
        return object : NetworkBoundResource<List<Movie>, PopularMovieResponse>() {
            override fun loadFromDb(): Flow<List<Movie>> {
                return localDataSource.getMovies(type = MovieType.POPULAR.name).map {
                    it.toMovieDomain()
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): PopularMovieResponse {
                return remoteDataSource.getPopularMovie()
            }

            override suspend fun saveCallResult(data: PopularMovieResponse) {
                localDataSource.insertMovies(data.toEntity())
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

    override fun updateMovieFavoriteState(movieId: Int, isFavorite: Boolean): Flow<Unit> {
        return flow {
            emit(
                localDataSource.updateMovieFavoriteState(movieId = movieId, isFavorite = isFavorite)
            )
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<Movie> {
        return object : NetworkBoundResource<Movie, MovieDetailsResponse>() {
            override fun loadFromDb(): Flow<Movie> {
                return localDataSource.getMovieById(movieId = movieId).map {
                    it.toMovieDomain()
                }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                return data?.runtime == 0
            }

            override suspend fun createCall(): MovieDetailsResponse {
                return remoteDataSource.getMovieDetails(movieId = movieId.toString())
            }

            override suspend fun saveCallResult(data: MovieDetailsResponse) {
                localDataSource.updateMovieDetails(
                    id = data.id,
                    status = data.status,
                    runtime = data.runtime,
                    voteAverage = data.voteAverage
                )
            }

        }.asFlow()
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            it.map { movieEntity ->
                movieEntity.toMovieDomain()
            }
        }
    }


}