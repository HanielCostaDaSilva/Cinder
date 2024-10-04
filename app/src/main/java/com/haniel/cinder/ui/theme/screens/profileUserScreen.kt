package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haniel.cinder.R
import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import com.haniel.cinder.usuarioLogado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUserScreen(
    modifier: Modifier = Modifier,
    navigateToEdition: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    onChatClick: () -> Unit,
    onProfileClick: () -> Unit,
    onMatchesClick: () -> Unit
) {
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val userDao = UserDAO()
    val globalLogin = usuarioLogado

    var message by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(globalLogin) {
        userDao.findByName(globalLogin.name) { fetchedUser ->
            user = fetchedUser
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
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Perfil", color = Color.White, fontFamily =  FontFamily.Serif)
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBarCinder(
                onHomeClick = navigateToHome,
                onProfileClick = onProfileClick,
                onChatClick = onChatClick,
                onMatchesClick = onMatchesClick,
                modifier = Modifier
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    user?.let {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,// Ensure spacing between elements
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Bem vindo ao seu Perfil, \"${usuarioLogado.name}\"!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = if (it.imageID != 0) it.imageID else R.drawable.cinder),
                                        contentDescription = "${it.name} Image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .clip(RoundedCornerShape(30.dp))
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        " ${it.name}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(" ${it.age} anos", fontSize = 18.sp,fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        " ${it.biograpy}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Button(
                                    onClick = { navigateToEdition() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                ) {
                                    Text("Editar Informações")
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = { showDialog = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                ) {
                                    Text("Excluir meu perfil", color = Color.Red)
                                }

                                message?.let {
                                    Text(
                                        text = it,
                                        color = if (it.contains("sucesso")) Color.Green else Color.Red,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(top = 10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    // Diálogo de confirmação
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Você tem certeza que deseja excluir seu perfil?") },
            confirmButton = {
                Button(
                    onClick = {
                        val userId = user?.id
                        if (userId != null) {
                            userDao.deleteUser(userId) { success ->
                                if (success) {
                                    message = "Perfil excluído com sucesso!"
                                        navigateToLogin()

                                } else {
                                    message = "Erro ao excluir o perfil."
                                }
                            }
                        } else {
                            message = "Erro: Usuário não encontrado."
                        }
                        showDialog = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
