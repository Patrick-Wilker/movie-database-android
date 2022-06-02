package com.patrickmota.moviedatabase.repository.credit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.patrickmota.moviedatabase.model.Cast
import com.patrickmota.moviedatabase.model.CreditResponse
import com.patrickmota.moviedatabase.model.Crew
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CreditRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var apiService: MovieApiService

    private lateinit var creditRepository: CreditRepository

    private lateinit var creditResponse: CreditResponse

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        creditRepository = CreditRepositoryImpl(apiService)

        creditResponse = creditResponse()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    private fun creditResponse(): CreditResponse {
        return CreditResponse(
            1, listOf(cast()), listOf(crew())
        )
    }

    private fun cast(): Cast {
        return Cast(
            1,
            true,
            3,
            "",
            "",
            "",
            1.0,
            "",
            1,
            "",
            "",
            1
        )
    }

    private fun crew(): Crew {
        return Crew(
            1,
            true,
            3,
            "",
            "",
            "",
            1.0,
            "",
            "",
            "",
            ""
        )
    }

    @Test
    fun testSuccess() {
        runBlocking {
            Mockito.`when`(apiService.getCredits(anyString(), anyString())).thenReturn(creditResponse)

            assertEquals(
                CreditRepositoryStatus.Success(creditResponse),
                creditRepository.retrieveCredit("")
            )
        }
    }

    @Test
    fun testError() {
        runBlocking {
            val t = RuntimeException()
            Mockito.`when`(apiService.getCredits(anyString(), anyString())).thenThrow(t)

            assertEquals(
                CreditRepositoryStatus.Error(t),
                creditRepository.retrieveCredit("")
            )
        }
    }

}