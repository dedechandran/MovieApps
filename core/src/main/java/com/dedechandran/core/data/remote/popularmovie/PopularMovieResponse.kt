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
        @SerializedName("release_date") val releaseDate: String?
    )
}

fun PopularMovieResponse.toEntity(): List<PopularMovieEntity> {
    return results.map {
        PopularMovieEntity(
            id = it.id,
            title = it.title,
            imageUrl = it.posterPath,
            overview = it.overview,
            releaseDate = it.releaseDate,
            isFavorite = false
        )
    }
}