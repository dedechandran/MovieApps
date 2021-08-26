package com.dedechandran.core.data.local.popularmovie

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM popular_movie")
    fun getAllMovies(): Flow<List<PopularMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(popularMovies: List<PopularMovieEntity>)

    @Update
    suspend fun updateMovie(popularMovie: PopularMovieEntity)
}