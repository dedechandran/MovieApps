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
    private val movieInteractor: MovieInteractor
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
            movieInteractor.getMovieGenre()
                .flatMapConcat {
                    genreList = it
                    movieInteractor.getPopularMovie()
                }
                .map {
                    movies = it
                    it.map { popularMovie ->
                        CardItem.Movie(
                            id = popularMovie.id,
                            urlImage = popularMovie.imageUrl,
                            title = popularMovie.title,
                            releaseDate = popularMovie.releaseDate.formatDate(),
                            genres = popularMovie.genres.split(",").map {
                                genreList.find { genre -> it.toInt() == genre.id }?.name
                            }.joinToString(","),
                            overview = popularMovie.overview,
                            isFavorite = popularMovie.isFavorite
                        )
                    }
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

    fun onFavoriteIconClicked(id: String) {
        val updatedMovie = movies.find { it.id == id }
        val newUpdatedMovie = updatedMovie?.copy(
            isFavorite = !updatedMovie.isFavorite
        )
        viewModelScope.launch {
            newUpdatedMovie?.let {
                movieInteractor.updateFavoriteMovieState(
                    movieId = updatedMovie.id.toInt(),
                    isFavorite = newUpdatedMovie.isFavorite
                )
                    .launchIn(this)
            }
        }
    }

}