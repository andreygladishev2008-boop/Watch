package com.dignerdranch.anroid.prakt_6

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.adapter.ChatPreviewAdapter
import com.dignerdranch.anroid.prakt_6.data.ChatRepository

class ChatListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.chatsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChatPreviewAdapter(ChatRepository.chats()) { chat ->
            startActivity(
                Intent(this, ChatActivity::class.java)
                    .putExtra(ChatActivity.EXTRA_CHAT_NAME, chat.name)
            )
        }
    }
}
