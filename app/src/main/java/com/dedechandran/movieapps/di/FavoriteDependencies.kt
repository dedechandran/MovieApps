package com.dedechandran.movieapps.di

import com.dedechandran.core.domain.movie.GetFavoriteMovieUseCase
import com.dedechandran.core.domain.movie.GetMovieDetailsUseCase
import com.dedechandran.core.domain.movie.GetMovieGenreUseCase
import com.dedechandran.core.domain.movie.UpdateFavoriteMovieStateUseCase
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