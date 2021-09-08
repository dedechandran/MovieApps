package com.dedechandran.core.data.remote

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres") val genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}
