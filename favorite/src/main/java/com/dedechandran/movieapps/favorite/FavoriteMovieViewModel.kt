package com.dedechandran.movieapps.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.movie.GetFavoriteMovieUseCase
import com.dedechandran.core.domain.movie.GetMovieGenreUseCase
import com.dedechandran.core.domain.movie.model.Genre
import com.dedechandran.core.domain.movie.model.Movie
import com.dedechandran.core.domain.movie.model.toDisplayItem
import com.dedechandran.core.ui.CardItem
import com.dedechandran.core.wrapper.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<Resource<List<CardItem>>>()
    val state : LiveData<Resource<List<CardItem>>> = _state

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
                    getFavoriteMovieUseCase.getFavoriteMovie()
                }
                .map {
                    movies = it
                    it.toDisplayItem(genreList = genreList)
                }
                .onStart { _state.value = Resource.Loading() }
                .onEach {
                    _state.value = Resource.Success(data = it)
                }
                .catch {
                    _state.value = Resource.Error(ERROR_MESSAGE)
                }
                .launchIn(this)
        }
        isInitialize = true
    }

    companion object {
        private const val ERROR_MESSAGE = "Something went wrong"
    }
}