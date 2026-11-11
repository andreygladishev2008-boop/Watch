package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.adapter.CollectionAdapter
import com.dignerdranch.anroid.prakt_6.data.CollectionsRepository

class CollectionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        findViewById<android.view.View>(R.id.backButton).setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.collectionsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CollectionAdapter(CollectionsRepository.collections()) { collection ->
            startActivity(
                Intent(this, MoviesActivity::class.java)
                    .putExtra(MoviesActivity.EXTRA_QUERY, collection.query)
                    .putExtra(MoviesActivity.EXTRA_TITLE, collection.title)
            )
        }
    }
}
