package com.patrickmota.moviedatabase.viewmodel.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.patrickmota.moviedatabase.model.Cast
import com.patrickmota.moviedatabase.model.CreditResponse
import com.patrickmota.moviedatabase.model.Crew
import com.patrickmota.moviedatabase.repository.credit.CreditRepository
import com.patrickmota.moviedatabase.repository.credit.CreditRepositoryStatus
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
class CreditViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var creditRepository: CreditRepository

    @Mock
    private lateinit var creditResponseObserver: Observer<CreditResponse>

    @Mock
    private lateinit var errorObserver: Observer<Boolean>

    private lateinit var creditViewModel: CreditViewModel
    private lateinit var creditResponse: CreditResponse

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        creditViewModel = CreditViewModel(creditRepository)
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
    fun verificaSeEstaChamandoAFuncaoDoRepositorioApenasUmaVez() {
        runBlocking {
            creditViewModel.getCreditResponse("")

            Mockito.verify(creditRepository, Mockito.times(1)).retrieveCredit("")
        }
    }

    @Test
    fun verificaCasoDeSucesso() {
        runBlocking {
            whenever(creditRepository.retrieveCredit("")).thenReturn(
                CreditRepositoryStatus.Success(creditResponse)
            )

            creditViewModel.getCreditResponse("")

            creditViewModel.creditResponse().observeForever(creditResponseObserver)

            verify(creditResponseObserver).onChanged(creditResponse)

        }
    }

    @Test
    fun verificaCasoDeErro() {
        runBlocking {
            val t = Throwable()
            whenever(creditRepository.retrieveCredit("")).thenReturn(
                CreditRepositoryStatus.Error(t)
            )

            creditViewModel.getCreditResponse("")

            creditViewModel.error().observeForever(errorObserver)

            verify(errorObserver).onChanged(true)

        }
    }

}