package com.dedechandran.movieapps.di

import com.dedechandran.core.domain.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteDependencies {
    fun getMovieUseCase(): MovieUseCase
}