package com.dedechandran.core.data.local.popularmovie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE movie_type = :type")
    fun getAllMovies(type: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("UPDATE movie SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateMovieFavoriteState(id: Int, isFavorite: Boolean)

    @Query("UPDATE movie SET status = :status, runtime = :runtime, vote_average = :voteAverage WHERE id = :id")
    suspend fun updateMovieDetails(
        id: Int,
        status: String?,
        runtime: Int?,
        voteAverage: Double?
    )

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Query("SELECT * FROM movie WHERE is_favorite = :isFavorite")
    fun getFavoriteMovies(isFavorite: Boolean = true): Flow<List<MovieEntity>>
}