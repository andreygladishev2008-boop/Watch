package com.dignerdranch.anroid.prakt_6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.R
import com.dignerdranch.anroid.prakt_6.data.Collection

class CollectionAdapter(
    private val collections: List<Collection>,
    private val onClick: (Collection) -> Unit
) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    inner class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(collection: Collection) {
            iconImageView.setImageResource(collection.iconResId)
            titleTextView.text = collection.title
            descriptionTextView.text = collection.description
            itemView.setOnClickListener { onClick(collection) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    override fun getItemCount(): Int = collections.size
}
