package com.dedechandran.movieapps.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dedechandran.core.domain.movie.GetMovieDetailsUseCase
import com.dedechandran.core.domain.movie.UpdateFavoriteMovieStateUseCase
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
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    private lateinit var updateFavoriteMovieStateUseCase: UpdateFavoriteMovieStateUseCase


    private lateinit var vm: DetailsViewModel

    @Before
    fun setUp() {
        vm = DetailsViewModel(
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            updateFavoriteMovieStateUseCase = updateFavoriteMovieStateUseCase
        )
        vm.state.observeForever { }
    }

    @Test
    fun `should initialize properly`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockMovieId = 1
        whenever(getMovieDetailsUseCase.getMovieDetails(mockMovieId)).thenReturn(flow {
            emit(createMockMovieDetails())
        })
        vm.initialize(mockMovieId)
        Truth.assertThat(vm.state.value).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `should fail to initialze`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockMovieId = 1
        whenever(getMovieDetailsUseCase.getMovieDetails(mockMovieId)).thenReturn(flow {
            throw Exception()
        })
        vm.initialize(mockMovieId)
        Truth.assertThat(vm.state.value).isInstanceOf(Resource.Error::class.java)
    }

    private fun createMockMovieDetails() = Movie(
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
}