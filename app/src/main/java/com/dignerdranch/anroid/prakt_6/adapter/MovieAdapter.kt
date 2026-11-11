package com.dignerdranch.anroid.prakt_6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.R
import com.dignerdranch.anroid.prakt_6.data.FavouritesRepository
import com.dignerdranch.anroid.prakt_6.data.OmdbMovie
import com.dignerdranch.anroid.prakt_6.util.PosterLoader

class MovieAdapter(
    private val movies: List<OmdbMovie>,
    private val onMovieClick: (OmdbMovie) -> Unit,
    private val onFavouriteClick: (OmdbMovie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        private val favouriteImageView: ImageView = itemView.findViewById(R.id.favouriteImageView)

        fun bind(movie: OmdbMovie) {
            titleTextView.text = movie.title
            yearTextView.text = listOf(movie.year, itemView.context.getString(R.string.movie))
                .filter { it.isNotBlank() }
                .joinToString(" • ")

            PosterLoader.load(posterImageView, movie.posterUrl)

            val favourite = FavouritesRepository.isFavourite(itemView.context, movie.imdbId)
            favouriteImageView.setImageResource(
                if (favourite) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )

            itemView.setOnClickListener { onMovieClick(movie) }
            favouriteImageView.setOnClickListener { onFavouriteClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}
