package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.haniel.cinder.R
import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import com.haniel.cinder.userService
import com.haniel.cinder.usuarioLogado

@Composable
fun PersonCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = if (user.imageID != 0) user.imageID else R.drawable.cinder),
                contentDescription = "${user.name} Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(" ${user.name}", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Idade: ")
                    }
                    append("${user.age} anos")
                },
                fontSize = 20.sp
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Interesses: ")
                    }
                    append(user.interests.joinToString(", "))
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
        }

    }
}

@Composable
fun BiograpyCard(user: User) {
    ElevatedCard(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = user.biograpy,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

val BackColor = Color(0xFF5A028F)
val contentColor = Color(0xFFE7E7E7)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CinderPrincipalScreen(
    modifier: Modifier = Modifier,
    userDao: UserDAO = UserDAO(),
    onProfile: () -> Unit,
    onChatClick: () -> Unit,
    onHomeClick: () -> Unit,
    onMatchesClick: () -> Unit
) {
    var indexPerson by remember { mutableIntStateOf(0) }
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var personDisplay by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var matchMessage by remember { mutableStateOf<String?>(null) }
    var usersWithInterests by remember { mutableStateOf<List<Pair<User, Int>>>(emptyList()) }

    // Obtenha o contexto atual
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userDao.find { loadedUsers ->
            userDao.findByName(usuarioLogado.name) { user ->
                if (user != null) {
                    usersWithInterests = loadedUsers
                        .filter { it.name != usuarioLogado.name }
                        .map { otherUser ->
                            otherUser to user.interests.intersect(otherUser.interests.toSet()).size
                        }
                        .sortedByDescending { it.second }

                    if (usersWithInterests.isNotEmpty()) {
                        personDisplay = usersWithInterests[indexPerson].first
                    }
                }
                isLoading = false
            }
        }
    }

    // Exibe o Toast quando houver uma mensagem de match
    LaunchedEffect(matchMessage) {
        matchMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            matchMessage = null
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackColor,
                    titleContentColor = contentColor,
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cinder", color = Color.White, fontFamily = FontFamily.Serif)
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBarCinder(
                onHomeClick = onHomeClick,
                onChatClick = onChatClick,
                onProfileClick = onProfile,
                onMatchesClick = onMatchesClick,
                modifier = modifier
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    personDisplay?.let { person ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            item {
                                PersonCard(person)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Magenta,
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier.width(150.dp),
                                        onClick = {
                                            val result = userService.sendMatch(
                                                userPrincipal = usuarioLogado,
                                                userToMatch = personDisplay!!
                                            )

                                            matchMessage = when (result) {
                                                -1 -> "Esse usuário já foi adicionado."
                                                0 -> "Será que vai rolar?"
                                                1 -> {
                                                    "Rolou um Match!"
                                                    onChatClick()
                                                    null
                                                }

                                                else -> null
                                            }
                                        },
                                    ) {
                                        Text("Match")
                                    }
                                    Button(
                                        modifier = Modifier.width(150.dp),
                                        onClick = {
                                            indexPerson =
                                                (indexPerson + 1) % usersWithInterests.size
                                            personDisplay = usersWithInterests[indexPerson].first
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                    ) {
                                        Text("Next")
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            item {
                                BiograpyCard(user = person)
                            }
                        }
                    }
                }
            }
        }
    )
}
@Preview(showBackground = true)
@Composable
fun CinderPrincipalScreenPreview() {
    CinderPrincipalScreen(
        onProfile = { /*TODO*/ },
        onChatClick = { /*TODO*/ },
        onHomeClick = { /*TODO*/ }) {

    }
}