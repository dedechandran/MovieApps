package com.dedechandran.movieapps.di

import com.dedechandran.core.domain.GetFavoriteMovieUseCase
import com.dedechandran.core.domain.GetMovieDetailsUseCase
import com.dedechandran.core.domain.GetMovieGenreUseCase
import com.dedechandran.core.domain.UpdateFavoriteMovieStateUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteDependencies {
    fun getFavoriteMovieUseCase(): GetFavoriteMovieUseCase
    fun getMovieDetailsUseCase(): GetMovieDetailsUseCase
    fun updateFavoriteMovieStateUseCase(): UpdateFavoriteMovieStateUseCase
    fun getMovieGenreUseCase(): GetMovieGenreUseCase
}