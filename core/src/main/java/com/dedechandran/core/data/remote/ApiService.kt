package com.dedechandran.core.data.remote

import com.dedechandran.core.data.remote.details.MovieDetailsResponse
import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/popular")
    suspend fun getMoviePopularList(): PopularMovieResponse

    @GET("genre/{type}/list")
    suspend fun getGenres(@Path("type") type: String): GenreResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: String): MovieDetailsResponse
}