package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dignerdranch.anroid.prakt_6.data.CollectionsRepository
import com.dignerdranch.anroid.prakt_6.data.FavouritesRepository
import com.dignerdranch.anroid.prakt_6.data.SessionManager

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }
        findViewById<View>(R.id.favouritesRow).setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
        findViewById<View>(R.id.logoutButton).setOnClickListener { confirmLogout() }
    }

    override fun onResume() {
        super.onResume()
        bindProfile()
    }

    private fun bindProfile() {
        findViewById<TextView>(R.id.nameTextView).text = SessionManager.currentName(this)
        findViewById<TextView>(R.id.emailTextView).text = SessionManager.currentEmail(this)
        findViewById<TextView>(R.id.favouritesCountTextView).text =
            FavouritesRepository.count(this).toString()
        findViewById<TextView>(R.id.collectionsCountTextView).text =
            CollectionsRepository.collections().size.toString()
        findViewById<TextView>(R.id.versionTextView).text = appVersion()
    }

    private fun appVersion(): String = runCatching {
        packageManager.getPackageInfo(packageName, 0).versionName.orEmpty()
    }.getOrDefault("1.0")

    private fun confirmLogout() {
        AlertDialog.Builder(this)
            .setTitle(R.string.logout)
            .setMessage(R.string.logout_confirm)
            .setPositiveButton(R.string.yes) { _, _ -> logout() }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun logout() {
        SessionManager.signOut(this)
        startActivity(
            Intent(this, SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        finish()
    }
}
