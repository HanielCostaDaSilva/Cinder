package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haniel.cinder.R
import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import com.haniel.cinder.usuarioLogadoCinder

@Composable
fun BottomAppBarPrincipal(
    onHomeClick: () -> Unit,
    onChatClick: () -> Unit,
    modifier: Modifier
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        IconButton(onClick = onHomeClick) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home"
            )
        }
        IconButton(onClick = onChatClick) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "Chat"
            )
        }

        Text(
            text = "Bem vindo, \"$usuarioLogadoCinder\"",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(16.dp)
        )

    }
}

@Composable
fun PersonCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
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
                    .padding(10.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Nome: ${user.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Idade: ${user.age}", fontSize = 18.sp)
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
                .fillMaxWidth()
        ) {
            Text(
                "Biografia",
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = user.biograpy,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun FavoritePersonButton(user: User, modifier: Modifier) {

    var favoriteIcon by remember { mutableStateOf(Icons.Filled.FavoriteBorder) }
    val favoriteIconFilled = Icons.Filled.FavoriteBorder
    val favoriteIconBorder = Icons.Filled.Favorite

    SmallFloatingActionButton(
        modifier = modifier.padding(10.dp),

        onClick = {
            favoriteIcon = if (favoriteIcon == favoriteIconBorder) {
                favoriteIconFilled
            } else {
                favoriteIconBorder
            }
            println("O usuário favoritou: ${user.name}")
        },
        shape = CircleShape,
        containerColor = Color.White,
        contentColor = Color.Red

    ) {
        Icon(favoriteIcon, contentDescription = "Large floating action button")
    }
}

val BackColor = Color(0xFF5A028F)
val contentColor = Color(0xFFE7E7E7)

@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun CinderPrincipalScreen(
    modifier: Modifier = Modifier,
    userDao: UserDAO = UserDAO(),
    onProfile: () -> Unit
) {
    var indexPerson by remember { mutableIntStateOf(0) }
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var personDisplay by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        userDao.find { loadedUsers ->
            users = loadedUsers
            if (users.isNotEmpty()) {
                personDisplay = users[indexPerson]
            }
            isLoading = false
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
                title = { Text("Cinder") },
                navigationIcon = {
                    IconButton(onClick = {/*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = contentColor
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = contentColor
                        )
                    }
                    IconButton(onClick = { onProfile() }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Perfil",
                            tint = contentColor
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBarPrincipal(
                onHomeClick = { /*TODO*/ },
                onChatClick = { /*TODO*/ },
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
                                            containerColor = Color.Red,
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier.width(150.dp),
                                        onClick = {
                                            indexPerson = (indexPerson + 1) % users.size
                                            personDisplay = users[indexPerson]
                                        },
                                    ) {
                                        Text("Match")
                                    }
                                    Button(
                                        modifier = Modifier.width(150.dp),
                                        onClick = {
                                            indexPerson =
                                                if (indexPerson - 1 < 0) users.size - 1
                                                else (indexPerson - 1) % users.size
                                            personDisplay = users[indexPerson]
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
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
                        FavoritePersonButton(
                            user = person,
                            Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                        )
                    }
                }

            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPrincipalPreview() {
//    val modifierScreen: Modifier = Modifier
//        .fillMaxSize()
//        .background(Color(0xFF1A1A1A))
//        .padding(16.dp)
//    CinderPrincipalScreen(modifier = modifierScreen)
//}
