package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dignerdranch.anroid.prakt_6.data.FavouritesRepository
import com.dignerdranch.anroid.prakt_6.data.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var favouritesCountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!SessionManager.isLoggedIn(this)) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }

        favouritesCountTextView = findViewById(R.id.favouritesCountTextView)

        findViewById<TextView>(R.id.userNameTextView).text = SessionManager.currentName(this)
        findViewById<TextView>(R.id.profileEmailTextView).text = SessionManager.currentEmail(this)

        findViewById<android.view.View>(R.id.collectionsCard).setOnClickListener {
            startActivity(Intent(this, CollectionsActivity::class.java))
        }
        findViewById<android.view.View>(R.id.discussionsCard).setOnClickListener {
            startActivity(Intent(this, ChatListActivity::class.java))
        }
        findViewById<android.view.View>(R.id.favouritesCard).setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
        findViewById<android.view.View>(R.id.profileCard).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        findViewById<android.view.View>(R.id.profileShortcut).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        findViewById<android.view.View>(R.id.searchShortcut).setOnClickListener {
            startActivity(
                Intent(this, MoviesActivity::class.java)
                    .putExtra(MoviesActivity.EXTRA_QUERY, "")
                    .putExtra(MoviesActivity.EXTRA_TITLE, getString(R.string.search_movies))
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (::favouritesCountTextView.isInitialized) {
            favouritesCountTextView.text = FavouritesRepository.count(this).toString()
        }
    }
}
