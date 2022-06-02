package com.patrickmota.moviedatabase.repository.credit

import com.patrickmota.moviedatabase.constant.Constant.API_KEY
import com.patrickmota.moviedatabase.model.CreditResponse
import com.patrickmota.moviedatabase.remote.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreditRepositoryImpl(private val apiService: MovieApiService) : CreditRepository {

    override suspend fun retrieveCredit(movieId: String): CreditRepositoryStatus<CreditResponse> {

        return withContext(Dispatchers.IO){
            try {
                val aux = apiService.getCredits(movieId, API_KEY)
                CreditRepositoryStatus.Success(aux)
            } catch (t: Throwable) {
                CreditRepositoryStatus.Error(t)
            }
        }

        //return apiService.getCredits(movieId, API_KEY)
    }
}