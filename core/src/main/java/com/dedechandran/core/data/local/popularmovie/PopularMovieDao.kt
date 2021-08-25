package com.dedechandran.core.data.local.popularmovie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM popular_movie")
    fun getAllPopularMovies(): Flow<List<PopularMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(popularMovies: List<PopularMovieEntity>)
}