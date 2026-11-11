package com.dignerdranch.anroid.prakt_6.data

import com.google.gson.annotations.SerializedName

data class OmdbMovie(
    @SerializedName("Title") val title: String = "",
    @SerializedName("Year") val year: String = "",
    @SerializedName("imdbID") val imdbId: String = "",
    @SerializedName("Type") val type: String = "",
    @SerializedName("Poster") val poster: String = ""
) {
    val posterUrl: String?
        get() = poster.takeIf { it.isNotBlank() && it != "N/A" }

    fun toFavourite(): FavouriteMovie = FavouriteMovie(
        imdbId = imdbId,
        title = title,
        year = year,
        poster = posterUrl.orEmpty()
    )
}

data class OmdbSearchResponse(
    @SerializedName("Search") val search: List<OmdbMovie>? = null,
    @SerializedName("totalResults") val totalResults: String? = null,
    @SerializedName("Response") val response: String? = null,
    @SerializedName("Error") val error: String? = null
)

data class OmdbDetails(
    @SerializedName("Title") val title: String = "",
    @SerializedName("Year") val year: String = "",
    @SerializedName("Rated") val rated: String = "",
    @SerializedName("Released") val released: String = "",
    @SerializedName("Runtime") val runtime: String = "",
    @SerializedName("Genre") val genre: String = "",
    @SerializedName("Director") val director: String = "",
    @SerializedName("Writer") val writer: String = "",
    @SerializedName("Actors") val actors: String = "",
    @SerializedName("Plot") val plot: String = "",
    @SerializedName("Language") val language: String = "",
    @SerializedName("Country") val country: String = "",
    @SerializedName("Awards") val awards: String = "",
    @SerializedName("Poster") val poster: String = "",
    @SerializedName("Metascore") val metascore: String = "",
    @SerializedName("imdbRating") val imdbRating: String = "",
    @SerializedName("imdbVotes") val imdbVotes: String = "",
    @SerializedName("imdbID") val imdbId: String = "",
    @SerializedName("Type") val type: String = "",
    @SerializedName("Response") val response: String? = null,
    @SerializedName("Error") val error: String? = null
) {
    val posterUrl: String?
        get() = poster.takeIf { it.isNotBlank() && it != "N/A" }

    fun toFavourite(): FavouriteMovie = FavouriteMovie(
        imdbId = imdbId,
        title = title,
        year = year,
        poster = posterUrl.orEmpty()
    )
}

data class FavouriteMovie(
    val imdbId: String,
    val title: String,
    val year: String,
    val poster: String
) {
    val posterUrl: String?
        get() = poster.takeIf { it.isNotBlank() && it != "N/A" }
}
