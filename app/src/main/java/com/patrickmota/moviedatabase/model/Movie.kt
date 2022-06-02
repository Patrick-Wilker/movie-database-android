package com.patrickmota.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("vote_avarage")
    val voteAvarage: Double,
    val title: String,
    @SerializedName("origianl_title")
    val origianlTitle: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val genres: List<Genre>
)