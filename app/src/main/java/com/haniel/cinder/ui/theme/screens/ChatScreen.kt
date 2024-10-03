package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haniel.cinder.model.Message
import com.haniel.cinder.model.User
import com.haniel.cinder.viewmodel.ChatViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ChatScreen(viewModel: ChatViewModel, user: User) {
    val messages by viewModel.messages.collectAsState(initial = emptyList())
    var messageContent by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .weight(1f)
            .padding(8.dp)) {
            items(messages) { message ->
                MessageItem(message)
            }
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageContent,
                onValueChange = { messageContent = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Digite uma mensagem") }
            )
            IconButton(onClick = {
                if (messageContent.isNotBlank()) {
                    viewModel.sendMessage(remetente = "Eu", conteudo = messageContent)
                    messageContent = ""
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        }
    }
}


@Composable
fun MessageItem(message: Message) {
    val alignment = if (message.remetente == "Eu") Alignment.End else Alignment.Start
    val backgroundColor = if (message.remetente == "Eu") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.remetente == "Eu") Arrangement.End else Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(backgroundColor),
            modifier = Modifier.padding(5.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = message.conteudo, color = Color.White)
                Text(text = formatTimestamp(message.data), fontSize = 12.sp, color = Color.LightGray)
            }
        }
    }
}

fun formatTimestamp(data: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(data))
}

@Preview(showBackground = true)
@Composable
fun DefaultChatPreview() {
    val modifierScreen: Modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1A1A1A))
        .padding(16.dp)
    ChatScreen(ChatViewModel(), User())
}