package com.patrickmota.moviedatabase.repository.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.patrickmota.moviedatabase.model.Genre
import com.patrickmota.moviedatabase.model.MovieDetailResponse
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
import org.mockito.Mockito.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var apiService: MovieApiService

    private lateinit var detailRepository: DetailRepository

    private lateinit var movieDetailResponse: MovieDetailResponse

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        detailRepository = DetailRepositoryImpl(apiService)

        movieDetailResponse = movieDetail()

    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    private fun movieDetail(): MovieDetailResponse {
        return MovieDetailResponse(
            1,
            "Rei leao",
            2,
            "otimo",
            "english",
            "The lion king",
            "",
            5.0,
            "",
            "",
            false,
            "",
            10,
            10,
            "",
            "",
            true,
            1.2,
            10,
            listOf(Genre(2, "ação"))
        )
    }

    @Test
    fun testSuccess() {
        runBlocking {
            whenever(apiService.getMovieDetail(anyString(), anyString())).thenReturn(
                movieDetailResponse
            )

            assertEquals(
                DetailRepositoryStatus.Success(movieDetailResponse),
                detailRepository.retrieveMovieDetail("")
            )
        }
    }

    @Test
    fun testError() {
        runBlocking {
            val t = RuntimeException()
            whenever(apiService.getMovieDetail(anyString(), anyString())).thenThrow(t)

            assertEquals(
                DetailRepositoryStatus.Error(t),
                detailRepository.retrieveMovieDetail("")
            )
        }
    }
}