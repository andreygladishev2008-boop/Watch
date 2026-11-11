package com.dignerdranch.anroid.prakt_6

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dignerdranch.anroid.prakt_6.data.FavouriteMovie
import com.dignerdranch.anroid.prakt_6.data.FavouritesRepository
import com.dignerdranch.anroid.prakt_6.data.OmdbClient
import com.dignerdranch.anroid.prakt_6.data.OmdbDetails
import com.dignerdranch.anroid.prakt_6.util.PosterLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMDB_ID = "extra_imdb_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_POSTER = "extra_poster"
    }

    private lateinit var titleTextView: TextView
    private lateinit var subtitleTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var plotTextView: TextView
    private lateinit var directorTextView: TextView
    private lateinit var actorsTextView: TextView
    private lateinit var posterImageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var favouriteButton: ImageView
    private lateinit var favouriteActionButton: TextView

    private var imdbId: String = ""
    private var currentMovie: FavouriteMovie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        titleTextView = findViewById(R.id.titleTextView)
        subtitleTextView = findViewById(R.id.subtitleTextView)
        genreTextView = findViewById(R.id.genreTextView)
        plotTextView = findViewById(R.id.plotTextView)
        directorTextView = findViewById(R.id.directorTextView)
        actorsTextView = findViewById(R.id.actorsTextView)
        posterImageView = findViewById(R.id.posterImageView)
        progressBar = findViewById(R.id.progressBar)
        favouriteButton = findViewById(R.id.favouriteButton)
        favouriteActionButton = findViewById(R.id.favouriteActionButton)

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }
        favouriteButton.setOnClickListener { toggleFavourite() }
        favouriteActionButton.setOnClickListener { toggleFavourite() }

        imdbId = intent.getStringExtra(EXTRA_IMDB_ID).orEmpty()

        val initialTitle = intent.getStringExtra(EXTRA_TITLE).orEmpty()
        val initialPoster = intent.getStringExtra(EXTRA_POSTER)
        if (initialTitle.isNotBlank()) {
            titleTextView.text = initialTitle
            findViewById<TextView>(R.id.headerTitleTextView).text = initialTitle
            currentMovie = FavouriteMovie(imdbId, initialTitle, "", initialPoster.orEmpty())
        }
        PosterLoader.load(posterImageView, initialPoster)
        updateFavouriteUi()

        if (imdbId.isBlank()) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadDetails()
    }

    private fun loadDetails() {
        progressBar.visibility = View.VISIBLE

        OmdbClient.api.movieDetails(
            apiKey = OmdbClient.API_KEY,
            imdbId = imdbId
        ).enqueue(object : Callback<OmdbDetails> {
            override fun onResponse(call: Call<OmdbDetails>, response: Response<OmdbDetails>) {
                progressBar.visibility = View.GONE
                val body = response.body()
                if (!response.isSuccessful || body == null || body.response == "False") {
                    Toast.makeText(
                        this@MovieDetailsActivity,
                        body?.error ?: getString(R.string.server_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                bindDetails(body)
            }

            override fun onFailure(call: Call<OmdbDetails>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@MovieDetailsActivity,
                    R.string.network_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun bindDetails(details: OmdbDetails) {
        currentMovie = details.toFavourite()

        titleTextView.text = details.title
        findViewById<TextView>(R.id.headerTitleTextView).text = details.title

        subtitleTextView.text = listOf(
            details.year,
            details.runtime,
            if (details.imdbRating.isNotBlank() && details.imdbRating != "N/A") {
                "IMDb ${details.imdbRating}"
            } else {
                ""
            }
        ).filter { it.isNotBlank() && it != "N/A" }.joinToString(" • ")

        genreTextView.text = details.genre.takeIf { it != "N/A" }.orEmpty()
        plotTextView.text = details.plot.takeIf { it != "N/A" }.orEmpty()
        directorTextView.text = getString(R.string.director) + ": " + details.director
        actorsTextView.text = getString(R.string.actors) + ": " + details.actors

        PosterLoader.load(posterImageView, details.posterUrl)
        updateFavouriteUi()
    }

    private fun toggleFavourite() {
        val movie = currentMovie ?: return
        if (movie.imdbId.isBlank()) return

        val added = FavouritesRepository.toggle(this, movie)
        Toast.makeText(
            this,
            if (added) R.string.added_to_favourites else R.string.removed_from_favourites,
            Toast.LENGTH_SHORT
        ).show()
        updateFavouriteUi()
    }

    private fun updateFavouriteUi() {
        val movie = currentMovie
        val favourite = movie != null &&
            movie.imdbId.isNotBlank() &&
            FavouritesRepository.isFavourite(this, movie.imdbId)

        favouriteButton.setImageResource(
            if (favourite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
        )
        favouriteActionButton.setText(
            if (favourite) R.string.remove_from_favourites else R.string.add_to_favourites
        )
    }
}
