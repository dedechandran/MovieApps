package com.dedechandran.core.domain.movie

import android.util.Log
import com.dedechandran.core.data.IMovieRepository
import com.dedechandran.core.domain.movie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(private val movieRepository: IMovieRepository) {

    fun getPopularMovie(): Flow<List<Movie>> {
        return movieRepository.getPopularMovie().catch {t -> Log.d("ERROR", t.stackTraceToString()) }.flowOn(Dispatchers.IO)
    }
}