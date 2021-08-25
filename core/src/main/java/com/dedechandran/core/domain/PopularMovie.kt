package com.dedechandran.core.domain

data class PopularMovie(
    val id: String,
    val title: String,
    val overview: String,
    val imageUrl: String,
    val releaseDate: String,
    val isFavorite: Boolean
)
