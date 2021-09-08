package com.dedechandran.movieapps.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.movie.GetMovieDetailsUseCase
import com.dedechandran.core.domain.movie.UpdateFavoriteMovieStateUseCase
import com.dedechandran.core.domain.movie.model.Movie
import com.dedechandran.core.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val updateFavoriteMovieStateUseCase: UpdateFavoriteMovieStateUseCase
) : ViewModel() {

    private var response: Movie? = null
    private val _state = MutableLiveData<Resource<Movie>>()
    val state = _state

    fun initialize(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase.getMovieDetails(movieId = movieId)
                .onEach {
                    _state.value = Resource.Success(it)
                    response = it
                }
                .catch {
                    _state.value = Resource.Error(ERROR_MESSAGE)
                }
                .launchIn(this)
        }
    }

    fun onFavoriteIconClicked(id: String) {
        response?.let {
            viewModelScope.launch {
                updateFavoriteMovieStateUseCase.updateFavoriteMovieState(
                    movieId = id.toInt(),
                    isFavorite = !it.isFavorite
                ).launchIn(this)
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "Something went wrong"
    }
}