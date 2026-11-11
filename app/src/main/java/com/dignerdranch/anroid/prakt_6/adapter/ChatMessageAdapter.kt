package com.dignerdranch.anroid.prakt_6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dignerdranch.anroid.prakt_6.R
import com.dignerdranch.anroid.prakt_6.data.ChatMessage

class ChatMessageAdapter(
    private val messages: List<ChatMessage>
) : RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder>() {

    companion object {
        private const val TYPE_RECEIVED = 0
        private const val TYPE_SENT = 1
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val authorTextView: TextView? = itemView.findViewById(R.id.authorTextView)

        fun bind(message: ChatMessage) {
            messageTextView.text = message.text
            timeTextView.text = message.time
            authorTextView?.text = message.author
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (messages[position].mine) TYPE_SENT else TYPE_RECEIVED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layout = if (viewType == TYPE_SENT) {
            R.layout.item_message_sent
        } else {
            R.layout.item_message_received
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size
}
