package com.dignerdranch.anroid.prakt_6.data

import android.content.Context
import java.security.MessageDigest

object SessionManager {
    private const val PREFS = "world_cinema_auth"

    private const val KEY_LOGGED_IN = "logged_in"
    private const val KEY_CURRENT_EMAIL = "current_email"
    private const val KEY_CURRENT_NAME = "current_name"
    private const val KEY_REGISTERED_EMAIL = "registered_email"
    private const val KEY_REGISTERED_NAME = "registered_name"
    private const val KEY_REGISTERED_PASSWORD = "registered_password"

    fun isLoggedIn(context: Context): Boolean =
        prefs(context).getBoolean(KEY_LOGGED_IN, false)

    fun currentEmail(context: Context): String =
        prefs(context).getString(KEY_CURRENT_EMAIL, "").orEmpty()

    fun currentName(context: Context): String {
        val name = prefs(context).getString(KEY_CURRENT_NAME, "").orEmpty()
        return name.ifBlank { currentEmail(context).substringBefore('@').ifBlank { "Гость" } }
    }

    /** @return null when the credentials are valid, otherwise a user-facing error. */
    fun validateSignIn(context: Context, email: String, password: String): String? {
        val normalizedEmail = email.trim().lowercase()
        if (!isValidEmail(normalizedEmail)) return "Введите корректный e-mail"
        if (password.length < 4) return "Пароль должен содержать минимум 4 символа"

        val prefs = prefs(context)
        val registeredEmail = prefs.getString(KEY_REGISTERED_EMAIL, "").orEmpty()
        if (registeredEmail.equals(normalizedEmail, ignoreCase = true)) {
            val hash = prefs.getString(KEY_REGISTERED_PASSWORD, "").orEmpty()
            if (hash.isNotEmpty() && hash != hashPassword(password)) {
                return "Неверный пароль"
            }
        }
        return null
    }

    fun signIn(context: Context, email: String) {
        val normalizedEmail = email.trim().lowercase()
        val prefs = prefs(context)
        val registeredEmail = prefs.getString(KEY_REGISTERED_EMAIL, "").orEmpty()
        val name = if (registeredEmail.equals(normalizedEmail, ignoreCase = true)) {
            prefs.getString(KEY_REGISTERED_NAME, "").orEmpty()
        } else {
            normalizedEmail.substringBefore('@')
        }
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, true)
            .putString(KEY_CURRENT_EMAIL, normalizedEmail)
            .putString(KEY_CURRENT_NAME, name.ifBlank { normalizedEmail.substringBefore('@') })
            .apply()
    }

    /** @return null when registration succeeds, otherwise a user-facing error. */
    fun register(
        context: Context,
        name: String,
        email: String,
        password: String,
        passwordRepeat: String
    ): String? {
        val normalizedEmail = email.trim().lowercase()
        if (name.trim().isEmpty()) return "Введите имя"
        if (!isValidEmail(normalizedEmail)) return "Введите корректный e-mail"
        if (password.length < 4) return "Пароль должен содержать минимум 4 символа"
        if (password != passwordRepeat) return "Пароли не совпадают"

        prefs(context).edit()
            .putString(KEY_REGISTERED_EMAIL, normalizedEmail)
            .putString(KEY_REGISTERED_NAME, name.trim())
            .putString(KEY_REGISTERED_PASSWORD, hashPassword(password))
            .putBoolean(KEY_LOGGED_IN, true)
            .putString(KEY_CURRENT_EMAIL, normalizedEmail)
            .putString(KEY_CURRENT_NAME, name.trim())
            .apply()
        return null
    }

    fun signOut(context: Context) {
        prefs(context).edit().putBoolean(KEY_LOGGED_IN, false).apply()
    }

    fun isValidEmail(email: String): Boolean =
        Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(email)

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
}
