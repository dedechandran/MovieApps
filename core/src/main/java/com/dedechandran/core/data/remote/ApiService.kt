package com.dedechandran.core.data.remote

import com.dedechandran.core.data.remote.popularmovie.PopularMovieResponse
import retrofit2.http.GET

interface ApiService {

    @GET("movie/popular")
    suspend fun getMoviePopularList(): PopularMovieResponse

    @GET("")
    suspend fun getTvShowList()
}