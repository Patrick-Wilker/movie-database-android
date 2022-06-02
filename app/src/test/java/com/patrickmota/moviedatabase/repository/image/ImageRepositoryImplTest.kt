package com.patrickmota.moviedatabase.repository.image

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.patrickmota.moviedatabase.model.Backdrop
import com.patrickmota.moviedatabase.model.ImageResponse
import com.patrickmota.moviedatabase.model.Poster
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ImageRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var apiService: MovieApiService

    private lateinit var imageRepository: ImageRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        imageRepository = ImageRepositoryImpl(apiService)

    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    private val imageResponse = imageResponse()

    private fun imageResponse(): ImageResponse {
        return ImageResponse(1, listOf(backdrop()), listOf(poster()))
    }

    private fun backdrop(): Backdrop {
        return Backdrop(
            2.0,
            "",
            1,
            "",
            5.1,
            1,
            2
        )
    }

    private fun poster(): Poster {
        return Poster(
            1.0,
            "",
            1,
            "",
            1.0,
            1,
            1
        )
    }

    @Test
    fun testSuccess() {
        runBlocking {
            whenever(apiService.getImages(anyString(), anyString())).thenReturn(imageResponse)

            assertEquals(
                ImageRepositoryStatus.Success(imageResponse),
                imageRepository.retrieveImage("")
            )
        }
    }

    @Test
    fun testError() {
        runBlocking {
            val t = RuntimeException()
            whenever(apiService.getImages(anyString(), anyString())).thenThrow(t)

            assertEquals(
                ImageRepositoryStatus.Error(t),
                imageRepository.retrieveImage("")
            )
        }
    }
}