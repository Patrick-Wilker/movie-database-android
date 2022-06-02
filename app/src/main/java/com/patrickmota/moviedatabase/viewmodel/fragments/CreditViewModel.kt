package com.patrickmota.moviedatabase.viewmodel.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrickmota.moviedatabase.model.CreditResponse
import com.patrickmota.moviedatabase.repository.credit.CreditRepository
import com.patrickmota.moviedatabase.repository.credit.CreditRepositoryStatus
import kotlinx.coroutines.launch

class CreditViewModel(private val repository: CreditRepository) : ViewModel() {

    private val creditResponse: MutableLiveData<CreditResponse> = MutableLiveData<CreditResponse>()
    private val error: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun creditResponse(): LiveData<CreditResponse> {
        return creditResponse
    }

    fun error(): LiveData<Boolean> {
        return error
    }

    fun getCreditResponse(id: String) {

        viewModelScope.launch {
            when (val result = repository.retrieveCredit(id)) {
                is CreditRepositoryStatus.Success<CreditResponse> -> creditResponse.value =
                    result.creditResponse!!
                else -> error.value = true
            }
        }


//        viewModelScope.launch {
//            creditResponse.value = repository.retrieveCredit(id)
//        }
    }
}