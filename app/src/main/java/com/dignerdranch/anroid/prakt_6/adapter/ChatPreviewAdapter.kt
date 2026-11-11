package com.dignerdranch.anroid.prakt_6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.R
import com.dignerdranch.anroid.prakt_6.data.ChatPreview

class ChatPreviewAdapter(
    private val chats: List<ChatPreview>,
    private val onClick: (ChatPreview) -> Unit
) : RecyclerView.Adapter<ChatPreviewAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val initialsTextView: TextView = itemView.findViewById(R.id.avatarInitialsTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)

        fun bind(chat: ChatPreview) {
            nameTextView.text = chat.name
            lastMessageTextView.text = chat.lastMessage

            if (chat.avatarResId != 0) {
                avatarImageView.setImageResource(chat.avatarResId)
                avatarImageView.visibility = View.VISIBLE
                initialsTextView.visibility = View.GONE
            } else {
                avatarImageView.visibility = View.GONE
                initialsTextView.visibility = View.VISIBLE
                initialsTextView.text = chat.initials
            }

            itemView.setOnClickListener { onClick(chat) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_preview, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int = chats.size
}
