package com.dedechandran.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dedechandran.core.data.local.popularmovie.MovieDao
import com.dedechandran.core.data.local.popularmovie.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun popularMovieDao(): MovieDao
}