package com.haniel.cinder.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.haniel.cinder.userService
import kotlinx.coroutines.launch

@Composable
fun MatchesScreen(currentUser: User, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        //Matches Recebidos
        Text(
            text = "Matches Recebidos",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(currentUser.matchSendList) { id ->
                MatchItem(id = id)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Matches Enviados
        Text(
            text = "Matches Enviados",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(currentUser.matchSendList) { id ->
                MatchItem(id = id)
            }
        }
    }
}

@Composable
fun MatchItem(id:String) {
    var name by remember { mutableStateOf(id) }

    LaunchedEffect(id) {
    userService.getById(id)?.let {
        name = it.name }
    }
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
            contentDescription = "Imagem ${id}",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )

        Spacer(modifier = Modifier.width(12.dp))


        Text(
            text = id,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}


suspend fun getUsersMatches(ids:List<String> ): List<User> {
    val usersGet= mutableListOf<User>();
    for (id in ids){
        userService.getById(id)?.let { usersGet.add(it) }
    }
    println(usersGet.toString())
    return usersGet
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
