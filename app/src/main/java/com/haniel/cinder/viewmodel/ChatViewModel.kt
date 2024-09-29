package com.haniel.cinder.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haniel.cinder.model.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel : ViewModel(){
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    init {
        viewModelScope.launch {
            delay(8000L)
            receiveMessage(
                Message(
                    id = UUID.randomUUID().toString(),
                    remetente = "Usuario 1",
                    conteudo = "Olá,esta é uma mensagem simulada!",
                    data = System.currentTimeMillis()
                )
            )
        }
    }

    fun sendMessage(remetente: String, conteudo: String) {
        val newMessage = Message(
            id = UUID.randomUUID().toString(),
            remetente = remetente,
            conteudo = conteudo,
            data = System.currentTimeMillis()
        )
        _messages.value += newMessage
    }

    fun receiveMessage(message: Message) {
        _messages.value += message
    }
}