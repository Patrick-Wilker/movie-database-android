package com.patrickmota.moviedatabase.repository.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.patrickmota.moviedatabase.model.Genre
import com.patrickmota.moviedatabase.model.Movie
import com.patrickmota.moviedatabase.model.MovieResponse
import com.patrickmota.moviedatabase.remote.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var apiService: MovieApiService

    private lateinit var homeRepository: HomeRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        homeRepository = HomeRepositoryImpl(apiService)

    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    private fun movie(): Movie {
        return Movie(
            1,
            2.5,
            "Rei leão",
            "The lion king",
            95.1,
            "reileao",
            "",
            "",
            "",
            listOf(Genre(2, "ação"))
        )
    }

    @Test
    fun testSuccess() {
        runBlocking {
            val movieResponse = MovieResponse(listOf(movie()))

            whenever(apiService.getNowPlayingMovies(any())).thenReturn(movieResponse)

            assertEquals(
                HomeRepositoryStatus.Success(movieResponse),
                homeRepository.retrieveNowPlayingMovie()
            )
        }
    }

    @Test
    fun testError() {
        runBlocking {
            val error = RuntimeException()
            whenever(apiService.getNowPlayingMovies(any())).thenThrow(error)
            assertEquals(
                HomeRepositoryStatus.Error(error),
                homeRepository.retrieveNowPlayingMovie()
            )
        }
    }

}