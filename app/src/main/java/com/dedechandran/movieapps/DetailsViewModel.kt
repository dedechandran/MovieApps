package com.dedechandran.movieapps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.GetPopularMovieDetails
import com.dedechandran.core.domain.PopularMovie
import com.dedechandran.core.domain.UpdateMovieFavoriteStateUseCase
import com.dedechandran.core.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPopularMovieDetails: GetPopularMovieDetails,
    private val updateMovieFavoriteStateUseCase: UpdateMovieFavoriteStateUseCase
) : ViewModel() {

    private var response: PopularMovie? = null
    private val _state = MutableLiveData<Resource<PopularMovie>>()
    val state = _state

    fun initialize(movieId: Int) {
        viewModelScope.launch {
            getPopularMovieDetails.getPopularMovieDetails(movieId = movieId)
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
                updateMovieFavoriteStateUseCase.updatePopularMovie(
                    movieId = id.toInt(),
                    isFavorite = !it.isFavorite
                ).launchIn(this)
            }
        }
    }

}