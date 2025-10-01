package com.example.rsaencrypted.encrypted

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.rsaencrypted.ChatMessage

class EncryptedLogsViewModel : ViewModel() {

    val logs: SnapshotStateList<String> = mutableStateListOf()
    val messages = mutableStateListOf<ChatMessage>()
    var rsa by mutableStateOf<RSAObject?>(null)
        private set

    fun log(message: String) {
        logs.add(message)
    }

    fun addMessage(text: String) {
        messages.add(ChatMessage(text = text))
    }

    fun InitializationKeys() {
        if (rsa==null) {
            log("Генерация RSA ключей...\n")
            rsa = RSAObject()
            log("ПУБЛИЧНЫЙ КЛЮЧ: ${rsa!!.n} + ${rsa!!.e}\n")
            log("ПРИВАТНЫЙ КЛЮЧ: ${rsa!!.d}\n")
        }
    }

    fun clear() {
        logs.clear()
    }
}