package com.dignerdranch.anroid.prakt_6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.R
import com.dignerdranch.anroid.prakt_6.data.FavouriteMovie
import com.dignerdranch.anroid.prakt_6.util.PosterLoader

class FavouriteAdapter(
    private val movies: List<FavouriteMovie>,
    private val onMovieClick: (FavouriteMovie) -> Unit,
    private val onDeleteClick: (FavouriteMovie) -> Unit
) : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.deleteImageView)

        fun bind(movie: FavouriteMovie) {
            titleTextView.text = movie.title
            yearTextView.text = movie.year
            PosterLoader.load(posterImageView, movie.posterUrl)

            itemView.setOnClickListener { onMovieClick(movie) }
            deleteImageView.setOnClickListener { onDeleteClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favourite, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}
