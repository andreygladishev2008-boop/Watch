package com.dignerdranch.anroid.prakt_6

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.adapter.ChatMessageAdapter
import com.dignerdranch.anroid.prakt_6.data.ChatMessage
import com.dignerdranch.anroid.prakt_6.data.ChatRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CHAT_NAME = "extra_chat_name"
    }

    private lateinit var chatName: String
    private lateinit var messages: MutableList<ChatMessage>
    private lateinit var adapter: ChatMessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatName = intent.getStringExtra(EXTRA_CHAT_NAME) ?: "Marvel"
        messages = ChatRepository.messages(chatName)

        recyclerView = findViewById(R.id.messagesRecyclerView)
        messageEditText = findViewById(R.id.messageEditText)

        findViewById<TextView>(R.id.chatTitleTextView).text = chatName
        findViewById<View>(R.id.backButton).setOnClickListener { finish() }
        findViewById<ImageView>(R.id.sendButton).setOnClickListener { sendMessage() }

        messageEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else {
                false
            }
        }

        adapter = ChatMessageAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recyclerView.adapter = adapter
        scrollToBottom()
    }

    private fun sendMessage() {
        val text = messageEditText.text.toString().trim()
        if (text.isEmpty()) return

        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        ChatRepository.addMessage(chatName, ChatMessage(text, true, "Вы", time))
        adapter.notifyItemInserted(messages.lastIndex)
        messageEditText.setText("")
        scrollToBottom()
    }

    private fun scrollToBottom() {
        if (messages.isNotEmpty()) {
            recyclerView.scrollToPosition(messages.lastIndex)
        }
    }
}
