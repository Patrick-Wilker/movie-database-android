package com.patrickmota.moviedatabase.remote

import com.patrickmota.moviedatabase.model.CreditResponse
import com.patrickmota.moviedatabase.model.ImageResponse
import com.patrickmota.moviedatabase.model.MovieDetailResponse
import com.patrickmota.moviedatabase.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") api_key: String): MovieResponse

    @GET("movie/upcoming")
    suspend fun getComingSoonMovies(@Query("api_key") api_key: String): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId: String,
        @Query("api_key") api_key: String
    ): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: String,
        @Query("api_key") api_key: String
    ): CreditResponse

    @GET("movie/{movie_id}/images")
    suspend fun getImages(
        @Path("movie_id") movieId: String,
        @Query("api_key") api_key: String
    ): ImageResponse
}