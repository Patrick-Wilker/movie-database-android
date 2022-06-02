package com.patrickmota.moviedatabase.viewmodel.activities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.patrickmota.moviedatabase.model.Genre
import com.patrickmota.moviedatabase.model.Movie
import com.patrickmota.moviedatabase.model.MovieResponse
import com.patrickmota.moviedatabase.repository.home.HomeRepository
import com.patrickmota.moviedatabase.repository.home.HomeRepositoryStatus
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
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var repository: HomeRepository

    @Mock
    private lateinit var movieResponseObserver: Observer<MovieResponse>

    @Mock
    private lateinit var errorObserver: Observer<Boolean>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    private fun instantiate(): MainViewModel {
        return MainViewModel(repository)
    }

    private val movieResponse = MovieResponse(listOf(movie()))

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
    fun verificaSeEstaChamandoAsFuncoesApenasUmaVez() {

        runBlocking {
            instantiate().getNowPlayingMovie()
            instantiate().getComingSoonMovie()

            verify(repository, times(1)).retrieveNowPlayingMovie()
            verify(repository, times(1)).retrieveComingSoonMovie()

        }
    }


    @Test
    fun whenTheRepositoryReturnsAnSuccessTheMovieResponserVariableMustChange() {

        runBlocking {
            whenever(repository.retrieveNowPlayingMovie()).thenReturn(
                HomeRepositoryStatus.Success(
                    movieResponse
                )
            )

            val mainViewModel = MainViewModel(repository)
            mainViewModel.movieResponse().observeForever(movieResponseObserver)
            mainViewModel.getNowPlayingMovie()

            verify(movieResponseObserver).onChanged(movieResponse)

        }

    }

    @Test
    fun whenTheRepositoryReturnsAnErrorTheErrorVariableMustBeTrue() {

        runBlocking {
            val t = Throwable()
            whenever(repository.retrieveNowPlayingMovie()).thenReturn(
                HomeRepositoryStatus.Error(t)
            )

            val mainViewModel = MainViewModel(repository)
            mainViewModel.error().observeForever(errorObserver)
            mainViewModel.getNowPlayingMovie()

            verify(errorObserver).onChanged(true)

        }

    }

    @Test
    fun whenTheComingSoonRepositoryReturnsAnSuccessTheMovieResponserVariableMustChange() {

        runBlocking {
            whenever(repository.retrieveComingSoonMovie()).thenReturn(
                HomeRepositoryStatus.Success(
                    movieResponse
                )
            )

            val mainViewModel = MainViewModel(repository)
            mainViewModel.movieResponse().observeForever(movieResponseObserver)
            mainViewModel.getComingSoonMovie()

            verify(movieResponseObserver).onChanged(movieResponse)

        }

    }

    @Test
    fun whenTheComingSoonRepositoryReturnsAnErrorTheErrorVariableMustBeTrue() {

        runBlocking {
            val t = Throwable()
            whenever(repository.retrieveComingSoonMovie()).thenReturn(
                HomeRepositoryStatus.Error(t)
            )

            val mainViewModel = MainViewModel(repository)
            mainViewModel.error().observeForever(errorObserver)
            mainViewModel.getComingSoonMovie()

            verify(errorObserver).onChanged(true)

        }

    }
}