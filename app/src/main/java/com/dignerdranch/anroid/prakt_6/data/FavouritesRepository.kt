package com.dignerdranch.anroid.prakt_6.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavouritesRepository {
    private const val PREFS = "world_cinema_favourites"
    private const val KEY_MOVIES = "movies"

    private val gson = Gson()

    fun getAll(context: Context): List<FavouriteMovie> {
        val json = prefs(context).getString(KEY_MOVIES, null) ?: return emptyList()
        return runCatching {
            val type = object : TypeToken<List<FavouriteMovie>>() {}.type
            gson.fromJson<List<FavouriteMovie>>(json, type) ?: emptyList()
        }.getOrDefault(emptyList())
    }

    fun isFavourite(context: Context, imdbId: String): Boolean =
        getAll(context).any { it.imdbId == imdbId }

    /** @return true when the movie was added, false when it was removed. */
    fun toggle(context: Context, movie: FavouriteMovie): Boolean {
        val movies = getAll(context).toMutableList()
        val existing = movies.indexOfFirst { it.imdbId == movie.imdbId }
        val added = if (existing >= 0) {
            movies.removeAt(existing)
            false
        } else {
            movies.add(0, movie)
            true
        }
        save(context, movies)
        return added
    }

    fun remove(context: Context, imdbId: String) {
        val movies = getAll(context).filterNot { it.imdbId == imdbId }
        save(context, movies)
    }

    fun count(context: Context): Int = getAll(context).size

    private fun save(context: Context, movies: List<FavouriteMovie>) {
        prefs(context).edit().putString(KEY_MOVIES, gson.toJson(movies)).apply()
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
}
