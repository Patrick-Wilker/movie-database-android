package com.patrickmota.moviedatabase.model

data class ImageResponse(
    val id: Int,
    val backdrops: List<Backdrop>,
    val posters: List<Poster>
)