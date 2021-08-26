package com.dedechandran.core.domain

import com.dedechandran.core.data.local.popularmovie.PopularMovieEntity

data class PopularMovie(
    val id: String,
    val title: String,
    val overview: String,
    val imageUrl: String,
    val releaseDate: String,
    val isFavorite: Boolean,
    val genres: String
)


fun PopularMovie.toEntity() = PopularMovieEntity(
    id = id.toInt(),
    title = title,
    overview = overview,
    imageUrl = imageUrl,
    releaseDate = releaseDate,
    isFavorite = isFavorite,
    genres = genres
)
