package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dignerdranch.anroid.prakt_6.data.SessionManager

class SignInActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        findViewById<TextView>(R.id.signInButton).setOnClickListener { signIn() }
        findViewById<TextView>(R.id.registerLink).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun signIn() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val error = SessionManager.validateSignIn(this, email, password)
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            return
        }

        SessionManager.signIn(this, email)
        openMain()
    }

    private fun openMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        finish()
    }
}
