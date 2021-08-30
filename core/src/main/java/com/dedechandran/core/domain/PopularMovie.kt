package com.dedechandran.core.domain

import com.dedechandran.core.data.local.popularmovie.MovieEntity

data class PopularMovie(
    val id: String,
    val title: String,
    val overview: String,
    val imageUrl: String,
    val releaseDate: String,
    val isFavorite: Boolean,
    val genres: String,
    val voteAverage: Double,
    val runtime: Int,
    val revenue: Long,
    val movieType: String = MovieType.POPULAR.name
)


fun PopularMovie.toEntity() = MovieEntity(
    id = id.toInt(),
    title = title,
    overview = overview,
    imageUrl = imageUrl,
    releaseDate = releaseDate,
    isFavorite = isFavorite,
    genres = genres,
    voteAverage = voteAverage,
    revenue = revenue,
    runtime = runtime,
    movieType = movieType
)
