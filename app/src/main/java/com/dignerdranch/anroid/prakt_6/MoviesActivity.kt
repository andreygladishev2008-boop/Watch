package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.adapter.MovieAdapter
import com.dignerdranch.anroid.prakt_6.data.FavouritesRepository
import com.dignerdranch.anroid.prakt_6.data.OmdbClient
import com.dignerdranch.anroid.prakt_6.data.OmdbMovie
import com.dignerdranch.anroid.prakt_6.data.OmdbSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "extra_query"
        const val EXTRA_TITLE = "extra_title"
    }

    private lateinit var searchEditText: EditText
    private lateinit var statusTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private var currentMovies: List<OmdbMovie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        searchEditText = findViewById(R.id.searchEditText)
        statusTextView = findViewById(R.id.statusTextView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.moviesRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }
        findViewById<ImageView>(R.id.searchButton).setOnClickListener { performSearch() }

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        val title = intent.getStringExtra(EXTRA_TITLE)
        if (!title.isNullOrBlank()) {
            findViewById<TextView>(R.id.titleTextView).text = title
        }

        val query = intent.getStringExtra(EXTRA_QUERY).orEmpty()
        if (query.isNotBlank()) {
            searchEditText.setText(query)
            performSearch()
        } else {
            showStatus(getString(R.string.enter_query))
        }
    }

    private fun performSearch() {
        val query = searchEditText.text.toString().trim()
        if (query.isEmpty()) {
            showStatus(getString(R.string.enter_query))
            return
        }

        currentMovies = emptyList()
        recyclerView.adapter = null
        statusTextView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        OmdbClient.api.searchMovies(
            apiKey = OmdbClient.API_KEY,
            query = query
        ).enqueue(object : Callback<OmdbSearchResponse> {
            override fun onResponse(
                call: Call<OmdbSearchResponse>,
                response: Response<OmdbSearchResponse>
            ) {
                progressBar.visibility = View.GONE
                val body = response.body()
                val movies = body?.search.orEmpty()

                if (!response.isSuccessful || body == null) {
                    showStatus(getString(R.string.server_error))
                    return
                }
                if (body.response == "False" || movies.isEmpty()) {
                    showStatus(body.error ?: getString(R.string.nothing_found))
                    return
                }

                showMovies(movies, body.totalResults)
            }

            override fun onFailure(call: Call<OmdbSearchResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                showStatus(getString(R.string.network_error))
            }
        })
    }

    private fun showMovies(movies: List<OmdbMovie>, totalResults: String?) {
        currentMovies = movies
        statusTextView.text = getString(R.string.found, totalResults ?: movies.size.toString())
        statusTextView.visibility = View.VISIBLE

        recyclerView.adapter = MovieAdapter(
            movies = movies,
            onMovieClick = { movie -> openDetails(movie) },
            onFavouriteClick = { movie -> toggleFavourite(movie) }
        )
    }

    private fun openDetails(movie: OmdbMovie) {
        startActivity(
            Intent(this, MovieDetailsActivity::class.java)
                .putExtra(MovieDetailsActivity.EXTRA_IMDB_ID, movie.imdbId)
                .putExtra(MovieDetailsActivity.EXTRA_TITLE, movie.title)
                .putExtra(MovieDetailsActivity.EXTRA_POSTER, movie.posterUrl)
        )
    }

    private fun toggleFavourite(movie: OmdbMovie) {
        val added = FavouritesRepository.toggle(this, movie.toFavourite())
        Toast.makeText(
            this,
            if (added) R.string.added_to_favourites else R.string.removed_from_favourites,
            Toast.LENGTH_SHORT
        ).show()

        val index = currentMovies.indexOf(movie)
        if (index >= 0) {
            recyclerView.adapter?.notifyItemChanged(index)
        }
    }

    private fun showStatus(message: String) {
        statusTextView.text = message
        statusTextView.visibility = View.VISIBLE
    }
}
