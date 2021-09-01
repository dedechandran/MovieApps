package com.dedechandran.core.data.remote.details

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("revenue") val revenue: Long?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("status") val status: String?
){
    data class Genre(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String
    )
}
