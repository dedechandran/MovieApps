package com.dedechandran.movieapps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.*
import com.dedechandran.core.ui.CardItem
import com.dedechandran.core.utils.formatDate
import com.dedechandran.core.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
    private val updateFavoriteMovieStateUseCase: UpdateFavoriteMovieStateUseCase
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
                .catch {
                    state.value = Resource.Error("Something went wrong")
                }
                .launchIn(this)
        }
        isInitialize = true
    }

    fun onFavoriteIconClicked(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteMovieStateUseCase.updateFavoriteMovieState(
                movieId = id.toInt(),
                isFavorite = !isFavorite
            ).launchIn(this)
        }
    }

}