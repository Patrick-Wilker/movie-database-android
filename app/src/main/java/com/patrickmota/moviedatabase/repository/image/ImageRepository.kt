package com.patrickmota.moviedatabase.repository.image

import com.patrickmota.moviedatabase.model.ImageResponse

sealed class ImageRepositoryStatus<out R> {
    data class Success<ImageResponse> (val imageResponse: ImageResponse) : ImageRepositoryStatus<ImageResponse>()
    data class Error (val error: Throwable) : ImageRepositoryStatus<Nothing>()
}

interface ImageRepository {

    suspend fun retrieveImage(movieId: String): ImageRepositoryStatus<ImageResponse>

}