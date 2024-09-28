package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haniel.cinder.R
import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import com.haniel.cinder.usuarioLogadoCinder
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomAppBarPrincipal(
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onProfileClick: () -> Unit,
    onChatClick: () -> Unit,
    modifier: Modifier
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        IconButton(
            onClick = onHomeClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home"
            )
        }
        IconButton(
            onClick = onFavoritesClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorites"
            )
        }
        IconButton(
            onClick = onProfileClick,
            modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile"
            )
        }
        IconButton(
            onClick = onChatClick,
            modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Account"
            )
        }

    }
}

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
                    .padding(10.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Nome: ${user.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
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
                fontSize = 18.sp
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

        onClick = { favoriteIcon = if (favoriteIcon == favoriteIconBorder) {
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

//@Composable
//fun FavoritePersonButton(user: User, modifier: Modifier) {
//
//    var favoriteIcon by remember { mutableStateOf(Icons.Filled.FavoriteBorder) }
//    val favoriteIconFilled = Icons.Filled.FavoriteBorder
//    val favoriteIconBorder = Icons.Filled.Favorite
//
//    SmallFloatingActionButton(
//        modifier = modifier.padding(10.dp),
//
//        onClick = {
//            favoriteIcon = if (favoriteIcon == favoriteIconBorder) {
//                favoriteIconFilled
//            } else {
//                favoriteIconBorder
//            }
//            println("O usuário favoritou: ${user.name}")
//        },
//        shape = CircleShape,
//        containerColor = Color.White,
//        contentColor = Color.Red
//
//    ) {
//        Icon(favoriteIcon, contentDescription = "Large floating action button")
//    }
//}

val BackColor = Color(0xFF5A028F)
val contentColor = Color(0xFFE7E7E7)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CinderPrincipalScreen(
    modifier: Modifier = Modifier,
    userDao: UserDAO = UserDAO(),
    onProfile: () -> Unit,
    onChatClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    var indexPerson by remember { mutableIntStateOf(0) }
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var personDisplay by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val loggedUser = remember { mutableStateOf<User?>(null) }
    val globalLogin = usuarioLogadoCinder

    LaunchedEffect(Unit) {
        userDao.findByName(globalLogin) { fetchedUser ->
            loggedUser.value = fetchedUser
        }
    }

    fun loadUsers() {
        // Carrega os usuários e ordena por número de interesses em comum
        userDao.find { loadedUsers ->
            users = loadedUsers.sortedByDescending { user ->
                calculateCommonInterests(loggedUser.value, user)
            }
            personDisplay = users.getOrNull(indexPerson)
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        loadUsers()
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
                        Text("Cinder", color = Color.White, fontFamily =  FontFamily.Serif)
                    }
                },
//                navigationIcon = {
//                    IconButton(onClick = {/*TODO*/ }) {
//                        Icon(
//                            imageVector = Icons.Default.Menu,
//                            contentDescription = "Menu",
//                            tint = contentColor
//                        )
//                    }
//                },
            )
        },
        bottomBar = {
            BottomAppBarCinder(
                onHomeClick = onHomeClick,
                onChatClick = onChatClick,
                onProfile = onProfile,
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
                            items(users) { user ->
                                PersonCard(user)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
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
                        BiograpyCard(user = person)
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

// Função para calcular o número de interesses em comum
fun calculateCommonInterests(user1: User?, user2: User): Int {
    if (user1 == null || user2 == null) {
        return 0
    }
    return user1.interests.intersect(user2.interests).count()
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