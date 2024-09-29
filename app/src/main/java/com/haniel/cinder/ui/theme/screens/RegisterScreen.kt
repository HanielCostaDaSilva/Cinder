package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.haniel.cinder.R
import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val userDAO = UserDAO()


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onRegister: () -> Unit = {},
    goToLogin: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var scope = rememberCoroutineScope()
    var imageUrl by remember { mutableStateOf("") }


    val defaultImageUrl = "https://cdn-icons-png.flaticon.com/512/6073/6073873.png"



    val modifierTextField = Modifier
        .padding(bottom = 24.dp)
        .fillMaxWidth()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        val imagem = painterResource(id = R.drawable.image_logo)
        Image(
            painter = imagem,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
        )
        Text(
            text = "Faça seu cadastro",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = login,
            onValueChange = { login = it },
            placeholder = { Text("Usuario") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 10.dp)
        )
        TextField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 10.dp)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Nome") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 10.dp)
        )

        TextField(
            value = age,
            onValueChange = {
                age = it
            },
            placeholder = { Text(" Idade") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 10.dp)
        )

        TextField(
            value = biography,
            onValueChange = {
                biography = it
            },
            placeholder = { Text(" Biografia") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 10.dp)
        )

        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            placeholder = { Text("URL da Imagem") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 10.dp)
        )

        Image(
            painter = rememberImagePainter(if (imageUrl.isNotEmpty()) imageUrl else defaultImageUrl),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )

 /*
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Nome") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        */
        mensagemErro?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            LaunchedEffect(Unit) {
                delay(3000)
                mensagemErro = null
            }
        }
        Button(
            onClick = {
                if (name.isEmpty() || age.isEmpty() || login.isEmpty() || senha.isEmpty()) {
                    mensagemErro = "Alguns campos não foram preenchidos"
                } else {
                    scope.launch {
                        val finalImageUrl = if (imageUrl.isEmpty()) defaultImageUrl else imageUrl

                        val usuario = User(name = login, password = senha, age = age.toIntOrNull() ?: 0 , biograpy = biography,  imageUrl = finalImageUrl)
                        userDAO.add(usuario) {
                            onRegister()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.background,
                containerColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp, 10.dp, 50.dp, 10.dp)
                .height(50.dp)
        ) {
            Text("Cadastre-se")
        }

        Text(
            text = "Possui conta?",
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )

        ClickableText(
            text = AnnotatedString("Acesse agora."),
            onClick = { goToLogin() },
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultRegisterPreview() {
    val modifierScreen: Modifier = Modifier
    RegisterScreen(modifier = modifierScreen)
}