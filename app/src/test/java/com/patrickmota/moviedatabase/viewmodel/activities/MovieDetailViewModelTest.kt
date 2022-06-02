package com.patrickmota.moviedatabase.viewmodel.activities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.patrickmota.moviedatabase.model.*
import com.patrickmota.moviedatabase.repository.detail.DetailRepository
import com.patrickmota.moviedatabase.repository.detail.DetailRepositoryStatus
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
class MovieDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var detailRepository: DetailRepository

    @Mock
    private lateinit var imageRepository: ImageRepository

    @Mock
    private lateinit var movieDetailResponseObserver: Observer<MovieDetailResponse>

    @Mock
    private lateinit var imageResponseObserver: Observer<ImageResponse>

    @Mock
    private lateinit var errorObserver: Observer<Boolean>

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        movieDetailViewModel = movieDetailViewModel()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    private val movieDetailResponse = movieDetail()

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

    private fun movieDetailViewModel(): MovieDetailViewModel {
        return MovieDetailViewModel(detailRepository, imageRepository)
    }

    @Test
    fun verificaSeEstaChamandoAFuncaoDoRepositorio() {
        runBlocking {
            movieDetailViewModel.getData("")
            movieDetailViewModel.getPhotos("")

            Mockito.verify(detailRepository, Mockito.times(1)).retrieveMovieDetail("")
            Mockito.verify(imageRepository, Mockito.times(1)).retrieveImage("")
        }
    }

    @Test
    fun verificaCasoDeSucessoAoBuscarDetalhesDosFilmes() {
        runBlocking {
            whenever(detailRepository.retrieveMovieDetail("")).thenReturn(
                DetailRepositoryStatus.Success(movieDetailResponse)
            )

            movieDetailViewModel.getData("")
            movieDetailViewModel.movieDetailResponse().observeForever(movieDetailResponseObserver)

            verify(movieDetailResponseObserver).onChanged(movieDetailResponse)

        }
    }

    @Test
    fun verificaCasoDeSucessoAoBuscarImagensDosFilmes() {
        runBlocking {
            whenever(imageRepository.retrieveImage("")).thenReturn(
                ImageRepositoryStatus.Success(imageResponse)
            )

            movieDetailViewModel.getPhotos("")
            movieDetailViewModel.imageResponse().observeForever(imageResponseObserver)

            verify(imageResponseObserver).onChanged(imageResponse)

        }
    }

    @Test
    fun verificaCasoDeError() {
        runBlocking {
            val t = Throwable()
            whenever(detailRepository.retrieveMovieDetail("")).thenReturn(
                DetailRepositoryStatus.Error(t)
            )

            whenever(imageRepository.retrieveImage("")).thenReturn(
                ImageRepositoryStatus.Error(t)
            )

            movieDetailViewModel.getData("")
            movieDetailViewModel.getPhotos("")
            movieDetailViewModel.error().observeForever(errorObserver)

            verify(errorObserver).onChanged(true)

        }
    }

}