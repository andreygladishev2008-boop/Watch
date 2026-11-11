package com.dignerdranch.anroid.prakt_6.util

import android.widget.ImageView
import com.dignerdranch.anroid.prakt_6.R
import com.squareup.picasso.Picasso

/**
 * Загрузка постеров фильмов по URL с помощью библиотеки Picasso.
 * Пока изображение загружается (или если оно недоступно),
 * показывается локальная заглушка placeholder_poster.
 */
object PosterLoader {
    fun load(imageView: ImageView, url: String?) {
        if (url.isNullOrBlank()) {
            imageView.setImageResource(R.drawable.placeholder_poster)
            return
        }
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder_poster)
            .error(R.drawable.placeholder_poster)
            .fit()
            .centerCrop()
            .into(imageView)
    }
}
