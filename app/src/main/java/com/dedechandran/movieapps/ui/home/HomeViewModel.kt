package com.dedechandran.movieapps.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.movie.GetMovieGenreUseCase
import com.dedechandran.core.domain.movie.GetPopularMovieUseCase
import com.dedechandran.core.domain.movie.UpdateFavoriteMovieStateUseCase
import com.dedechandran.core.domain.movie.model.Genre
import com.dedechandran.core.domain.movie.model.Movie
import com.dedechandran.core.domain.movie.model.toDisplayItem
import com.dedechandran.core.ui.CardItem
import com.dedechandran.core.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getMovieGenreUseCase: GetMovieGenreUseCase
) : ViewModel() {

    private val _state = MutableLiveData<Resource<List<CardItem>>>()
    val state = _state

    private lateinit var genreList: List<Genre>
    private lateinit var movies: List<Movie>
    private var isInitialize = false

    @FlowPreview
    fun initialize() {
        if (isInitialize) return
        viewModelScope.launch {
            getMovieGenreUseCase.getMovieGenre()
                .flatMapConcat {
                    genreList = it
                    getPopularMovieUseCase.getPopularMovie()
                }
                .map {
                    movies = it
                    it.toDisplayItem(genreList = genreList)
                }
                .onStart { state.value = Resource.Loading() }
                .onEach {
                    state.value = Resource.Success(data = it)
                }
                .catch { t ->
                    state.value = Resource.Error(ERROR_MESSAGE)
                }
                .launchIn(this)
        }
        isInitialize = true
    }

    companion object {
        private const val ERROR_MESSAGE = "Something went wrong"
    }

}