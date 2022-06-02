package com.patrickmota.moviedatabase.viewmodel.activities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.patrickmota.moviedatabase.model.Backdrop
import com.patrickmota.moviedatabase.model.ImageResponse
import com.patrickmota.moviedatabase.model.Poster
import com.patrickmota.moviedatabase.repository.image.ImageRepository
import com.patrickmota.moviedatabase.repository.image.ImageRepositoryStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhotoViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var imageRepository: ImageRepository

    @Mock
    private lateinit var imageResponseObserver: Observer<ImageResponse>

    @Mock
    private lateinit var errorObserver: Observer<Boolean>

    private lateinit var photoViewModel: PhotoViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        photoViewModel = photoViewModel()
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

    private fun photoViewModel(): PhotoViewModel {
        return PhotoViewModel(imageRepository)
    }

    @Test
    fun verificaSeEstaChamandoAFuncaoDoRepositorioApenasUmaVez() {
        runBlocking {
            photoViewModel.getImage("")

            Mockito.verify(imageRepository, Mockito.times(1)).retrieveImage("")
        }
    }

    @Test
    fun verificaCasoDeSucesso() {
        runBlocking {
            whenever(imageRepository.retrieveImage("")).thenReturn(
                ImageRepositoryStatus.Success(imageResponse)
            )

            photoViewModel.getImage("")

            photoViewModel.imageResponse().observeForever(imageResponseObserver)

            verify(imageResponseObserver).onChanged(imageResponse)

        }
    }

    @Test
    fun verificaCasoDeError() {
        runBlocking {
            val t = Throwable()
            whenever(imageRepository.retrieveImage("")).thenReturn(
                ImageRepositoryStatus.Error(t)
            )

            photoViewModel.getImage("")
            photoViewModel.error().observeForever(errorObserver)

            verify(errorObserver).onChanged(true)

        }
    }
}