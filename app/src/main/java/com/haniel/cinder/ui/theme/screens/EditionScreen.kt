package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import com.haniel.cinder.usuarioLogado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditionScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var biograpy by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    val userDao = UserDAO()
    val globalLogin = usuarioLogado// A variável global que contém o login

    LaunchedEffect(globalLogin) {
        userDao.findByName(globalLogin.name) { fetchedUser ->
            user = fetchedUser
            if (fetchedUser != null) {
                name = fetchedUser.name
                age = fetchedUser.age.toString()
                biograpy = fetchedUser.biograpy
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
                title = { Text("Edição de Perfil") },
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
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nome") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = age,
                            onValueChange = { age = it },
                            label = { Text("Idade") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = biograpy,
                            onValueChange = { biograpy = it },
                            label = { Text("Biografia") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                val ageInt = age.toIntOrNull()
                                if (name.isNotBlank() && ageInt != null) {
                                    isSaving = true
                                    val updatedUser =
                                        user?.copy(name = name, age = ageInt, biograpy = biograpy)
                                    updatedUser?.let {
                                        userDao.updateUser(it) {
                                            isSaving = false
                                            message = "Dados atualizados com sucesso!"
                                            onBack()
                                        }
                                    }
                                } else {
                                    message = "Preencha todos os campos corretamente."
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Salvar")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        message?.let {
                            Text(
                                text = it,
                                color = if (it.contains("sucesso")) Color.Green else Color.Red,
                                fontSize = 16.sp
                            )
                        }
                        if (isSaving) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    )
}
