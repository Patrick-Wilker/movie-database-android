package com.patrickmota.moviedatabase.viewmodel.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrickmota.moviedatabase.model.ImageResponse
import com.patrickmota.moviedatabase.repository.image.ImageRepository
import com.patrickmota.moviedatabase.repository.image.ImageRepositoryStatus
import kotlinx.coroutines.launch

class PhotoViewModel(private val repository: ImageRepository) : ViewModel() {

    private val imageResponse: MutableLiveData<ImageResponse> = MutableLiveData<ImageResponse>()
    private val error: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun imageResponse(): LiveData<ImageResponse> {
        return imageResponse
    }

    fun error(): LiveData<Boolean>{
        return error
    }

    fun getImage(id: String) {
        viewModelScope.launch {
            when (val result = repository.retrieveImage(id)) {
                is ImageRepositoryStatus.Success<ImageResponse> -> imageResponse.value = result.imageResponse!!
                else -> error.value = true
            }
        }
    }
}