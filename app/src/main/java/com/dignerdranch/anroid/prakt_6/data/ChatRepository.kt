package com.dignerdranch.anroid.prakt_6.data

import com.dignerdranch.anroid.prakt_6.R

data class ChatPreview(
    val name: String,
    val lastMessage: String,
    val avatarResId: Int,
    val initials: String
)

data class ChatMessage(
    val text: String,
    val mine: Boolean,
    val author: String,
    val time: String
)

object ChatRepository {
    /** Messages are cached here so a conversation survives screen rotation and re-entry. */
    private val messageStore = mutableMapOf<String, MutableList<ChatMessage>>()

    fun chats(): List<ChatPreview> = listOf(
        ChatPreview("Marvel", "Обсуждаем финальную сцену…", R.drawable.avatar_1, "MV"),
        ChatPreview("The Witcher", "Второй сезон уже вышел!", R.drawable.avatar_2, "TW"),
        ChatPreview("LOST", "Кто понял концовку?", R.drawable.avatar_3, "LS"),
        ChatPreview("The Magicians", "Новая серия — просто огонь", 0, "TM")
    )

    fun messages(chatName: String): MutableList<ChatMessage> =
        messageStore.getOrPut(chatName) { initialMessages(chatName) }

    fun addMessage(chatName: String, message: ChatMessage) {
        messages(chatName).add(message)
    }

    private fun initialMessages(chatName: String): MutableList<ChatMessage> {
        val companion = when (chatName) {
            "The Witcher" -> "Геральт"
            "LOST" -> "Джек"
            "The Magicians" -> "Квентин"
            else -> "Ник"
        }
        return mutableListOf(
            ChatMessage("Привет! Смотрел последнюю серию?", false, companion, "19:42"),
            ChatMessage("Да, конечно! Финал получился очень сильным.", true, "Вы", "19:43"),
            ChatMessage("А как тебе визуальные эффекты и музыка?", false, companion, "19:44"),
            ChatMessage("На уровне большого кино. Особенно финальная сцена!", true, "Вы", "19:45"),
            ChatMessage("Согласен. Давай обсудим подробнее завтра.", false, companion, "19:46")
        )
    }
}
