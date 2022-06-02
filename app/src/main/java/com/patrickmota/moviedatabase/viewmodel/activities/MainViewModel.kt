package com.patrickmota.moviedatabase.viewmodel.activities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.patrickmota.moviedatabase.model.Genre
import com.patrickmota.moviedatabase.model.Movie
import com.patrickmota.moviedatabase.model.MovieResponse
import com.patrickmota.moviedatabase.repository.home.HomeRepository
import com.patrickmota.moviedatabase.repository.home.HomeRepositoryStatus
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel(private val repository: HomeRepository) : ViewModel() {

    private val movieResponse: MutableLiveData<MovieResponse> = MutableLiveData<MovieResponse>()
    private val error: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun movieResponse(): LiveData<MovieResponse> {
        return movieResponse
    }

    fun error(): LiveData<Boolean>{
        return error
    }

    fun getNowPlayingMovie() {
        viewModelScope.launch {

            when (val result = repository.retrieveNowPlayingMovie()) {
                is HomeRepositoryStatus.Success<MovieResponse> -> movieResponse.value =
                    result.movieResponse!!
                else -> error.value = true
            }
        }
//        viewModelScope.launch {
//            movieResponse.value = repository.retrieveNowPlayingMovie()
//        }
    }

    fun getComingSoonMovie() {
        viewModelScope.launch {

            when (val result = repository.retrieveComingSoonMovie()) {
                is HomeRepositoryStatus.Success<MovieResponse> -> movieResponse.value =
                    result.movieResponse!!
                else -> error.value = true
            }
        }
//        viewModelScope.launch {
//            movieResponse.value = repository.retrieveComingSoonMovie()
//        }
    }

    fun retrieveMovieListByGenre(genre: Genre): LiveData<List<Movie>> {
        return movieResponse.map { movieResponse ->
            movieResponse.results.filter { movie ->
                movie.genres.contains(
                    genre
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sortByDate(): LiveData<List<Movie>> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return movieResponse.map { movieResponse ->
            movieResponse.results.sortedByDescending {
                LocalDate.parse(it.releaseDate, dateTimeFormatter)
            }
        }
    }

    fun sortByAlphabetical(): LiveData<List<Movie>> {
        return movieResponse.map { movieResponse -> movieResponse.results.sortedBy { it.title } }
    }

}