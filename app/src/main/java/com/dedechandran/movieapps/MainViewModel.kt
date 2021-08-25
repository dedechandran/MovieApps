package com.dedechandran.movieapps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dedechandran.core.domain.GetPopularMovieUseCase
import com.dedechandran.core.ui.CardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModel() {

    val popularMovies = getPopularMovieUseCase.getPopularMovie()
        .map {
            it.data.orEmpty().map { popularMovie ->
                CardItem.Movie(
                    id = popularMovie.id,
                    urlImage = popularMovie.imageUrl
                )
            }
        }
        .asLiveData()

}