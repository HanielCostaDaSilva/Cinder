package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import com.haniel.cinder.usuarioLogado
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Lista de interesses predefinidos agrupados por categorias
val predefinedInterests = listOf(
    "Esportes" to listOf("Futebol", "Basquete", "Vôlei", "Tênis", "Natação", "Artes Marciais", "Corrida"),
    "Filmes" to listOf("Ação", "Comédia", "Drama", "Terror", "Ficção Científica", "Romance"),
    "Música" to listOf("Rock", "Pop", "Country", "Eletrônica", "Clássica"),
    "Política" to listOf("Liberalismo", "Conservadorismo", "Socialismo", "Comunismo", "Indiferente")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestsScreen(
    navController: NavHostController,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val userDao = UserDAO()
    val globalLogin = usuarioLogado
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(globalLogin) {
        userDao.findByName(globalLogin.name) { fetchedUser ->
            user = fetchedUser
            isLoading = false
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Your interests") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(predefinedInterests) { category ->
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = category.first,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    category.second.forEach { interest ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = user?.interests?.contains(interest) ?: false,
                                                onCheckedChange = { isChecked ->
                                                    // Atualiza a lista de interesses do usuário
                                                    val updatedUser = user?.copy(
                                                        interests = if (isChecked) {
                                                            user?.interests?.toMutableList()?.apply {
                                                                add(interest)
                                                            }
                                                        } else {
                                                            user?.interests?.toMutableList()?.apply {
                                                                remove(interest)
                                                            }
                                                        } ?: mutableListOf()
                                                    )
                                                    user = updatedUser
                                                }
                                            )
                                            Text(text = interest)
                                        }
                                    }
                                }
                            }
                        }
                        Button(
                            onClick = {
                                user?.let {
                                    userDao.updateUser(it) {
                                        if (it) {
                                            onSave()
                                        } else {
                                            // Lidar com o erro de atualização
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Text("Salvar Interesses")
                        }
                    }
                }
            }
        }
    )
}
