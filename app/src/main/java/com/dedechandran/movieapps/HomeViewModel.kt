package com.dedechandran.movieapps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedechandran.core.domain.*
import com.dedechandran.core.ui.CardItem
import com.dedechandran.core.wrapper.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
    private val updateMovieFavoriteStateUseCase: UpdateMovieFavoriteStateUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow<UiState<List<CardItem>>>(UiState.Loading)
    val state = uiState

    private lateinit var genreList: List<Genre>
    private lateinit var popularMovies: List<PopularMovie>

    @FlowPreview
    fun initialize() {
        viewModelScope.launch {
            getMovieGenreUseCase.getMovieGenre()
                .flatMapConcat {
                    genreList = it
                    getPopularMovieUseCase.getPopularMovie()
                }
                .map {
                    popularMovies = it
                    it.map { popularMovie ->
                        CardItem.Movie(
                            id = popularMovie.id,
                            urlImage = popularMovie.imageUrl,
                            title = popularMovie.title,
                            releaseDate = popularMovie.releaseDate,
                            genres = popularMovie.genres.split(",").map {
                                genreList.find { genre -> it.toInt() == genre.id }?.name
                            }.joinToString(","),
                            overview = popularMovie.overview,
                            isFavorite = popularMovie.isFavorite
                        )
                    }
                }
                .onEach {
                    state.value = UiState.Success(data = it)
                }
                .catch {
                    state.value = UiState.Error("Something went wrong")
                }
                .launchIn(this)
        }
    }

    fun onFavoriteIconClicked(id: String) {
        val updatedMovie = popularMovies.find { it.id == id }
        val newUpdatedMovie = updatedMovie?.copy(
            isFavorite = !updatedMovie.isFavorite
        )
        viewModelScope.launch {
            newUpdatedMovie?.let {
                updateMovieFavoriteStateUseCase.updatePopularMovie(
                    movieId = updatedMovie.id.toInt(),
                    isFavorite = newUpdatedMovie.isFavorite
                )
                    .launchIn(this)
            }
        }
    }

}