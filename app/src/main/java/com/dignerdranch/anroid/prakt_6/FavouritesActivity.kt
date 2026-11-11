package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.adapter.FavouriteAdapter
import com.dignerdranch.anroid.prakt_6.data.FavouritesRepository

class FavouritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        recyclerView = findViewById(R.id.favouritesRecyclerView)
        emptyStateLayout = findViewById(R.id.emptyStateLayout)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }
        findViewById<View>(R.id.findMoviesButton).setOnClickListener {
            startActivity(
                Intent(this, MoviesActivity::class.java)
                    .putExtra(MoviesActivity.EXTRA_QUERY, "")
                    .putExtra(MoviesActivity.EXTRA_TITLE, getString(R.string.search_movies))
            )
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavourites()
    }

    private fun loadFavourites() {
        val movies = FavouritesRepository.getAll(this)

        emptyStateLayout.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
        recyclerView.visibility = if (movies.isEmpty()) View.GONE else View.VISIBLE

        recyclerView.adapter = FavouriteAdapter(
            movies = movies,
            onMovieClick = { movie ->
                startActivity(
                    Intent(this, MovieDetailsActivity::class.java)
                        .putExtra(MovieDetailsActivity.EXTRA_IMDB_ID, movie.imdbId)
                        .putExtra(MovieDetailsActivity.EXTRA_TITLE, movie.title)
                        .putExtra(MovieDetailsActivity.EXTRA_POSTER, movie.posterUrl)
                )
            },
            onDeleteClick = { movie ->
                FavouritesRepository.remove(this, movie.imdbId)
                loadFavourites()
            }
        )
    }
}
