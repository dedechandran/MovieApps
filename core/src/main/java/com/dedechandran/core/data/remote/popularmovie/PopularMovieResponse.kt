package com.dedechandran.core.data.remote.popularmovie

import com.dedechandran.core.data.local.popularmovie.PopularMovieEntity
import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<PopularMovie>
) {
    data class PopularMovie(
        @SerializedName("id") val id: Int,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("release_date") val releaseDate: String?,
        @SerializedName("genre_ids") val genres: List<Int>?
    )
}

fun PopularMovieResponse.toEntity(): List<PopularMovieEntity> {
    return results.map {
        PopularMovieEntity(
            id = it.id,
            title = it.title,
            imageUrl = "http://image.tmdb.org/t/p/w185${it.posterPath}",
            overview = it.overview,
            releaseDate = it.releaseDate,
            isFavorite = false,
            genres = it.genres?.joinToString(",") ?: "-"
        )
    }
}