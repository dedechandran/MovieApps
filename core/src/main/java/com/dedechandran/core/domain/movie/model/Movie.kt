package com.dedechandran.core.domain.movie.model

import com.dedechandran.core.ui.CardItem
import com.dedechandran.core.utils.formatDate

data class Movie(
    val id: String,
    val title: String,
    val overview: String,
    val imageUrl: String,
    val releaseDate: String,
    val isFavorite: Boolean,
    val genres: String,
    val voteAverage: Double,
    val runtime: Int,
    val status: String,
    val movieType: String = MovieType.POPULAR.name
)

fun List<Movie>.toDisplayItem(genreList: List<Genre>) = map { movie ->
    CardItem.Movie(
        id = movie.id,
        urlImage = movie.imageUrl,
        title = movie.title,
        releaseDate = movie.releaseDate.formatDate(),
        genres = movie.genres.split(",").map {
            genreList.find { genre -> it.toInt() == genre.id }?.name
        }.joinToString(","),
        overview = movie.overview,
        isFavorite = movie.isFavorite
    )
}