package com.patrickmota.moviedatabase.model

data class CreditResponse(
    val id: Int,
    var cast: List<Cast>,
    val crew: List<Crew>
)