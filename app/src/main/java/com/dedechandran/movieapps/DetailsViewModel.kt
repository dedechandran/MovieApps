package com.dedechandran.movieapps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.Movie
import com.dedechandran.core.domain.MovieInteractor
import com.dedechandran.core.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
) : ViewModel() {

    private var response: Movie? = null
    private val _state = MutableLiveData<Resource<Movie>>()
    val state = _state

    fun initialize(movieId: Int) {
        viewModelScope.launch {
            movieInteractor.getMovieDetails(movieId = movieId)
                .onEach {
                    _state.value = Resource.Success(it)
                    response = it
                }
                .catch {
                    _state.value = Resource.Error("Something went wrong")
                }
                .launchIn(this)
        }
    }

    fun onFavoriteIconClicked(id: String) {
        response?.let {
            viewModelScope.launch {
                movieInteractor.updateFavoriteMovieState(
                    movieId = id.toInt(),
                    isFavorite = !it.isFavorite
                ).launchIn(this)
            }
        }
    }

}