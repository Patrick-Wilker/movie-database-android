package com.patrickmota.moviedatabase.viewmodel.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrickmota.moviedatabase.model.ImageResponse
import com.patrickmota.moviedatabase.model.MovieDetailResponse
import com.patrickmota.moviedatabase.repository.detail.DetailRepository
import com.patrickmota.moviedatabase.repository.detail.DetailRepositoryStatus
import com.patrickmota.moviedatabase.repository.image.ImageRepository
import com.patrickmota.moviedatabase.repository.image.ImageRepositoryStatus
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val detailRepository: DetailRepository, private val imageRepository: ImageRepository) : ViewModel() {

    private val movieDetailResponse: MutableLiveData<MovieDetailResponse> =
        MutableLiveData<MovieDetailResponse>()
    private val imageResponse: MutableLiveData<ImageResponse> = MutableLiveData<ImageResponse>()
    private val error: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun movieDetailResponse(): LiveData<MovieDetailResponse> {
        return movieDetailResponse
    }

    fun imageResponse(): LiveData<ImageResponse> {
        return imageResponse
    }

    fun error(): LiveData<Boolean>{
        return error
    }

    fun getData(movieId: String) {

        viewModelScope.launch {
            when (val result = detailRepository.retrieveMovieDetail(movieId)) {
                is DetailRepositoryStatus.Success<MovieDetailResponse> -> movieDetailResponse.value = result.movieDetailResponse!!
                else -> error.value = true
            }
        }

//        viewModelScope.launch {
//            movieDetailResponse.value = detailRepository.retrieveMovieDetail(movieId)
//        }
    }

    fun getPhotos(movieId: String) {

        viewModelScope.launch {
            when (val result = imageRepository.retrieveImage(movieId)) {
                is ImageRepositoryStatus.Success<ImageResponse> -> imageResponse.value = result.imageResponse!!
                else -> error.value = true
            }
        }


//        viewModelScope.launch {
//            imageResponse.value = imageRepository.retrieveImage(movieId)
//        }
    }

}