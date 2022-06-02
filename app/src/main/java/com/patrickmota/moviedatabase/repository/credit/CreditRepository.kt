package com.patrickmota.moviedatabase.repository.credit

import com.patrickmota.moviedatabase.model.CreditResponse

sealed class CreditRepositoryStatus<out R> {
    data class Success<CreditResponse> (val creditResponse: CreditResponse) : CreditRepositoryStatus<CreditResponse>()
    data class Error (val error: Throwable) : CreditRepositoryStatus<Nothing>()
}

interface CreditRepository {

    suspend fun retrieveCredit(movieId: String): CreditRepositoryStatus<CreditResponse>

}