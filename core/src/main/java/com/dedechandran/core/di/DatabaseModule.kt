package com.dedechandran.core.di

import android.content.Context
import androidx.room.Room
import com.dedechandran.core.data.AppDatabase
import com.dedechandran.core.data.local.popularmovie.PopularMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie-db"
        ).build()
    }

    @Provides
    fun providePopularMovieDao(appDatabase: AppDatabase): PopularMovieDao {
        return appDatabase.popularMovieDao()
    }

}