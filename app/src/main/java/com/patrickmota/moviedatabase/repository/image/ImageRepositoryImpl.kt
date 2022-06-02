package com.patrickmota.moviedatabase.repository.image

import com.patrickmota.moviedatabase.constant.Constant.API_KEY
import com.patrickmota.moviedatabase.model.ImageResponse
import com.patrickmota.moviedatabase.remote.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepositoryImpl(private val apiService: MovieApiService) : ImageRepository {

    override suspend fun retrieveImage(movieId: String): ImageRepositoryStatus<ImageResponse> {

        return withContext(Dispatchers.IO){
            try {
                val aux = apiService.getImages(movieId, API_KEY)
                ImageRepositoryStatus.Success(aux)
            } catch (t: Throwable) {
                ImageRepositoryStatus.Error(t)
            }
        }

//        return apiService.getImages(movieId, API_KEY)
    }
}