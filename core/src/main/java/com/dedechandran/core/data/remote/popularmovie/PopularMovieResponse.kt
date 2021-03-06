package com.dedechandran.core.data.remote.popularmovie

import com.dedechandran.core.data.local.popularmovie.MovieEntity
import com.dedechandran.core.domain.movie.model.MovieType
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

fun PopularMovieResponse.toEntity(): List<MovieEntity> {
    return results.map {
        MovieEntity(
            id = it.id,
            title = if (it.title.isNullOrEmpty()) null else it.title,
            imageUrl = if (it.posterPath.isNullOrEmpty()) null else "http://image.tmdb.org/t/p/w185${it.posterPath}",
            overview = if (it.overview.isNullOrEmpty()) null else it.overview,
            releaseDate = if (it.releaseDate.isNullOrEmpty()) null else it.releaseDate,
            isFavorite = false,
            genres = if (it.genres.isNullOrEmpty()) null else it.genres.joinToString(","),
            status = null,
            voteAverage = null,
            movieType = MovieType.POPULAR.name,
            runtime = null
        )
    }
}
