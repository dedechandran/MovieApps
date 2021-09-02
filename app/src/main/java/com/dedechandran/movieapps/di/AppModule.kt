package com.dedechandran.movieapps.di

import com.dedechandran.core.domain.MovieInteractor
import com.dedechandran.core.domain.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase {
        return movieInteractor
    }

}