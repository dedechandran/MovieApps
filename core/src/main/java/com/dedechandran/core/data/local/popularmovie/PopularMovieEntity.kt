package com.dedechandran.core.data.local.popularmovie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dedechandran.core.domain.PopularMovie

@Entity(tableName = "popular_movie")
data class PopularMovieEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
)

fun List<PopularMovieEntity>.toDomain(): List<PopularMovie>{
    return this.map {
        PopularMovie(
            id = it.id.toString(),
            title = it.title ?: "-",
            imageUrl = it.imageUrl ?: "-",
            isFavorite = it.isFavorite,
            overview = it.overview ?: "-",
            releaseDate = it.releaseDate ?: "-"
        )
    }
}
