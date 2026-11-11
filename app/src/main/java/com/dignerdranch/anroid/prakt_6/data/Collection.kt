package com.dignerdranch.anroid.prakt_6.data

/** A curated movie collection shown on the Collections screen. */
data class Collection(
    val title: String,
    val description: String,
    val iconResId: Int,
    val query: String
)

object CollectionsRepository {
    fun collections(): List<Collection> = listOf(
        Collection("Marvel", "Супергерои и большие истории", com.dignerdranch.anroid.prakt_6.R.drawable.logo_movie, "Avengers"),
        Collection("DC", "Бэтмен, Супермен и другие", com.dignerdranch.anroid.prakt_6.R.drawable.ic_movies, "Batman"),
        Collection("Star Wars", "Давным-давно в далёкой галактике", com.dignerdranch.anroid.prakt_6.R.drawable.movie_ico, "Star Wars"),
        Collection("Комедии", "Для хорошего настроения", com.dignerdranch.anroid.prakt_6.R.drawable.ico_discussions, "Comedy"),
        Collection("Ужасы", "Фильмы, которые щекочут нервы", com.dignerdranch.anroid.prakt_6.R.drawable.ico_favourites, "Horror")
    )
}
