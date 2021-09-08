package com.dedechandran.core.di

import com.dedechandran.core.data.IMovieRepository
import com.dedechandran.core.data.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository {
        return movieRepository
    }
}