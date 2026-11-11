package com.dignerdranch.anroid.prakt_6.data

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface OmdbApi {
    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String,
        @Query("type") type: String = "movie",
        @Query("page") page: Int = 1
    ): Call<OmdbSearchResponse>

    @GET("/")
    fun movieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full"
    ): Call<OmdbDetails>
}

object OmdbClient {
    /**
     * Публичный демонстрационный ключ OMDB.
     * Если ключ перестанет работать, замените его на свой с https://www.omdbapi.com/apikey.aspx
     */
    const val API_KEY = "564727fa"

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: OmdbApi = retrofit.create(OmdbApi::class.java)
}
