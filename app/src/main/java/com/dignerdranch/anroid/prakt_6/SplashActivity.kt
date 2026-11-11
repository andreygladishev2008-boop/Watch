package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dignerdranch.anroid.prakt_6.data.SessionManager

class SplashActivity : AppCompatActivity() {

    private val splashDelay = 1400L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val next = if (SessionManager.isLoggedIn(this)) {
                MainActivity::class.java
            } else {
                SignInActivity::class.java
            }
            startActivity(Intent(this, next))
            finish()
        }, splashDelay)
    }
}
