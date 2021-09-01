package com.dedechandran.core.data.local.popularmovie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dedechandran.core.domain.PopularMovie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "genres") val genres: String,
    @ColumnInfo(name = "runtime") val runtime: Int?,
    @ColumnInfo(name = "vote_average") val voteAverage: Double?,
    @ColumnInfo(name = "movie_type") val movieType: String,
    @ColumnInfo(name = "status") val status: String?
)

fun List<MovieEntity>.toPopularMovieDomain(): List<PopularMovie> {
    return this.map {
        PopularMovie(
            id = it.id.toString(),
            title = it.title ?: "-",
            imageUrl = it.imageUrl ?: "-",
            isFavorite = it.isFavorite,
            overview = it.overview ?: "-",
            releaseDate = it.releaseDate ?: "-",
            genres = it.genres,
            voteAverage = it.voteAverage ?: 0.0,
            movieType = it.movieType,
            status = it.status ?: "-",
            runtime = it.runtime ?: 0
        )
    }
}

fun MovieEntity.toPopularMovieDomain(): PopularMovie {
    return PopularMovie(
        id = id.toString(),
        title = title ?: "-",
        imageUrl = imageUrl ?: "-",
        isFavorite = isFavorite,
        overview = overview ?: "-",
        releaseDate = releaseDate ?: "-",
        genres = genres,
        voteAverage = voteAverage ?: 0.0,
        movieType = movieType,
        status = status ?: "-",
        runtime = runtime ?: 0
    )
}


