package com.patrickmota.moviedatabase.repository.home

import com.patrickmota.moviedatabase.constant.Constant.API_KEY
import com.patrickmota.moviedatabase.model.MovieResponse
import com.patrickmota.moviedatabase.remote.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(private val apiService: MovieApiService) : HomeRepository {

    override suspend fun retrieveNowPlayingMovie(): HomeRepositoryStatus<MovieResponse> {

        return withContext(Dispatchers.IO){
            try {
                val aux = apiService.getNowPlayingMovies(API_KEY)
                HomeRepositoryStatus.Success(aux)
            } catch (t: Throwable) {
                HomeRepositoryStatus.Error(t)
            }
        }

//        return apiService.getNowPlayingMovies(API_KEY)
    }

    override suspend fun retrieveComingSoonMovie(): HomeRepositoryStatus<MovieResponse> {
        return withContext(Dispatchers.IO){
            try {
                val aux = apiService.getComingSoonMovies(API_KEY)
                HomeRepositoryStatus.Success(aux)
            } catch (t: Throwable) {
                HomeRepositoryStatus.Error(t)
            }
        }
//        return apiService.getComingSoonMovies(API_KEY)
    }
}