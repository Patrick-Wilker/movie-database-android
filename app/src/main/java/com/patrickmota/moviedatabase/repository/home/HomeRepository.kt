package com.patrickmota.moviedatabase.repository.home

import com.patrickmota.moviedatabase.model.MovieResponse

sealed class HomeRepositoryStatus<out R> {
    data class Success<MovieResponse> (val movieResponse: MovieResponse) : HomeRepositoryStatus<MovieResponse>()
    data class Error (val error: Throwable) : HomeRepositoryStatus<Nothing>()
}

interface HomeRepository {

    suspend fun retrieveNowPlayingMovie(): HomeRepositoryStatus<MovieResponse>

    suspend fun retrieveComingSoonMovie(): HomeRepositoryStatus<MovieResponse>

}