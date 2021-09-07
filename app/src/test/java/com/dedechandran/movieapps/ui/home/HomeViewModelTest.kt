package com.dedechandran.movieapps.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dedechandran.core.domain.movie.GetMovieGenreUseCase
import com.dedechandran.core.domain.movie.GetPopularMovieUseCase
import com.dedechandran.core.domain.movie.model.Genre
import com.dedechandran.core.domain.movie.model.Movie
import com.dedechandran.core.wrapper.Resource
import com.dedechandran.movieapps.CoroutineTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var getMovieGenreUseCase: GetMovieGenreUseCase

    @Mock
    private lateinit var getPopularMovieUseCase: GetPopularMovieUseCase

    private lateinit var vm: HomeViewModel

    @Before
    fun setUp() {
        vm = HomeViewModel(
            getMovieGenreUseCase = getMovieGenreUseCase,
            getPopularMovieUseCase = getPopularMovieUseCase
        )
        vm.state.observeForever { }
    }

    @Test
    fun `should initialize properly`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        whenever(getMovieGenreUseCase.getMovieGenre()).thenReturn(flow {
            emit(createMockGenre())
        })
        whenever(getPopularMovieUseCase.getPopularMovie()).thenReturn(flow {
            emit(createMockPopularMovie())
        })
        vm.initialize()
        verify(getMovieGenreUseCase).getMovieGenre()
        verify(getPopularMovieUseCase).getPopularMovie()
        Truth.assertThat(vm.state.value).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `should fail to initialize`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        whenever(getMovieGenreUseCase.getMovieGenre()).thenReturn(flow {
            throw Exception()
        })
        vm.initialize()
        verify(getMovieGenreUseCase).getMovieGenre()
        Truth.assertThat(vm.state.value).isInstanceOf(Resource.Error::class.java)
    }

    private fun createMockGenre() = listOf(
        Genre(id = 1, name = "genre1"),
        Genre(id = 2, name = "genre2"),
        Genre(id = 3, name = "genre3"),
        Genre(id = 4, name = "genre4")
    )

    private fun createMockPopularMovie() = listOf(
        Movie(
            id = "1",
            title = "movie1",
            overview = "overview1",
            imageUrl = "url",
            releaseDate = "2020-12-08",
            isFavorite = false,
            genres = "1,2,3",
            voteAverage = 8.0,
            runtime = 120,
            status = "Released",
        )
    )


}