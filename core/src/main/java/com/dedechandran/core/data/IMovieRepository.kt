package com.dedechandran.core.data

import com.dedechandran.core.domain.PopularMovie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovie(): Flow<Resource<List<PopularMovie>>>
}