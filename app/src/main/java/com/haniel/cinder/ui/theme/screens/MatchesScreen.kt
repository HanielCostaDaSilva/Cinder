package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haniel.cinder.R
import com.haniel.cinder.model.User

@Composable
fun MatchesScreen(currentUser: User, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Título de Matches Recebidos
        Text(
            text = "Matches Recebidos",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Matches Recebidos
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(currentUser.matchReceivedList) { matchId ->
                MatchItem(matchId = matchId)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Título de Matches Enviados
        Text(
            text = "Matches Enviados",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Matches Enviados
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(currentUser.matchSendList) { matchId ->
                MatchItem(matchId = matchId)
            }
        }
    }
}

@Composable
fun MatchItem(matchId: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.cinder),
            contentDescription = "Imagem do usuário",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )

        Spacer(modifier = Modifier.width(12.dp))


        Text(
            text = "Usuário $matchId", // Exemplo de formatação de texto
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MatchesScreenPreview() {
    val mockUser = User(
        matchReceivedList = mutableListOf("1", "2", "3"),
        matchSendList = mutableListOf("4", "5", "6")
    )

    MatchesScreen(currentUser = mockUser)
}
