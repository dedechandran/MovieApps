package com.dedechandran.movieapps.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.*
import com.dedechandran.core.ui.CardItem
import com.dedechandran.core.utils.formatDate
import com.dedechandran.core.wrapper.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val updateFavoriteMovieStateUseCase: UpdateFavoriteMovieStateUseCase
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
                .onStart { _state.value = Resource.Loading() }
                .onEach {
                    _state.value = Resource.Success(data = it)
                }
                .catch {
                    _state.value = Resource.Error("Something went wrong")
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
                updateFavoriteMovieStateUseCase.updateFavoriteMovieState(
                    movieId = updatedMovie.id.toInt(),
                    isFavorite = newUpdatedMovie.isFavorite
                )
                    .launchIn(this)
            }
        }
    }
}