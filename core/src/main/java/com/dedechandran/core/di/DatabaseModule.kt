package com.dedechandran.core.di

import android.content.Context
import androidx.room.Room
import com.dedechandran.core.data.AppDatabase
import com.dedechandran.core.data.local.popularmovie.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("moviedb".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie-db"
        )
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun providePopularMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.popularMovieDao()
    }

}