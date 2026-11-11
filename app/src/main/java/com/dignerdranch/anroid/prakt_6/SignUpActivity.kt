package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dignerdranch.anroid.prakt_6.data.SessionManager

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rePasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        rePasswordEditText = findViewById(R.id.rePasswordEditText)

        findViewById<TextView>(R.id.registerButton).setOnClickListener { register() }
        findViewById<TextView>(R.id.signInLink).setOnClickListener { finish() }
    }

    private fun register() {
        val error = SessionManager.register(
            this,
            nameEditText.text.toString(),
            emailEditText.text.toString(),
            passwordEditText.text.toString(),
            rePasswordEditText.text.toString()
        )
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            return
        }

        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        finish()
    }
}
