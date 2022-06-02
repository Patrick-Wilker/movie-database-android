package com.patrickmota.moviedatabase.repository.detail

import com.patrickmota.moviedatabase.model.MovieDetailResponse

sealed class DetailRepositoryStatus<out R> {
    data class Success<MovieDetailResponse> (val movieDetailResponse: MovieDetailResponse) : DetailRepositoryStatus<MovieDetailResponse>()
    data class Error (val error: Throwable) : DetailRepositoryStatus<Nothing>()
}

interface DetailRepository {

    suspend fun retrieveMovieDetail(movieI: String): DetailRepositoryStatus<MovieDetailResponse>

}