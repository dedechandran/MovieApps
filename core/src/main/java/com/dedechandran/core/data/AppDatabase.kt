package com.dedechandran.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dedechandran.core.data.local.popularmovie.PopularMovieDao
import com.dedechandran.core.data.local.popularmovie.PopularMovieEntity

@Database(entities = [PopularMovieEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun popularMovieDao(): PopularMovieDao
}