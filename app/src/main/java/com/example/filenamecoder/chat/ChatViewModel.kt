package com.example.filenamecoder.chat

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filenamecoder.rsa.RSA
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<Message>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(text: String, isUser: Boolean = true, context: Context) {
        messages.add(Message(text, "user"))
        if (isUser) {
            viewModelScope.launch {
                val rsa = RSA(1000)
                val encText = rsa.encrypt(text)
                val decText = rsa.decrypt(encText)

                messages.add(Message("Закодинованное название файла: $encText", "ai"))
                messages.add(Message("Раскодированное название файла: $decText", "user"))
            }
        }
    }
}

data class Message(val content: String, val role: String) {
    val isUser: Boolean
        get() = role == "user"
}