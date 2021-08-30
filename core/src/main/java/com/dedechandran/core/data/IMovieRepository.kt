package com.dedechandran.core.data

import com.dedechandran.core.domain.Genre
import com.dedechandran.core.domain.PopularMovie
import com.dedechandran.core.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovie(): Flow<List<PopularMovie>>
    fun getMovieGenres(): Flow<List<Genre>>
    fun updateMovieFavoriteState(movieId: Int, isFavorite: Boolean): Flow<Unit>
    fun getFavoriteMovieDetails(movieId: Int): Flow<PopularMovie>
}