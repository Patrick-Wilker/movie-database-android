package com.patrickmota.moviedatabase.repository.detail

import com.patrickmota.moviedatabase.constant.Constant.API_KEY
import com.patrickmota.moviedatabase.model.MovieDetailResponse
import com.patrickmota.moviedatabase.remote.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailRepositoryImpl(private val apiService: MovieApiService) : DetailRepository {

    override suspend fun retrieveMovieDetail(movieId: String): DetailRepositoryStatus<MovieDetailResponse> {

        return withContext(Dispatchers.IO){
            try {
                val aux = apiService.getMovieDetail(movieId, API_KEY)
                DetailRepositoryStatus.Success(aux)
            } catch (t: Throwable) {
                DetailRepositoryStatus.Error(t)
            }
        }

        //return apiService.getMovieDetail(movieId, API_KEY)
    }

}