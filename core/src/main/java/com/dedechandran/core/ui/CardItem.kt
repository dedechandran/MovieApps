package com.dedechandran.core.ui

sealed class CardItem{
    data class Movie(
        val id: String,
        val urlImage: String?,
        val title: String?,
        val overview: String?,
        val releaseDate: String?,
        val genres: String?,
        val isFavorite: Boolean
    ): CardItem()
}
