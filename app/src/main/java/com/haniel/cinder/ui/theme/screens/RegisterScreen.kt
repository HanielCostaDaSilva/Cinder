package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier,
                   onRegister: () -> Unit = {},
                   goTo: () -> Unit = {}) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    val modifierTextField = Modifier.padding(bottom = 24.dp).fillMaxWidth()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "Registre-se",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Nome") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = modifierTextField
        )
        TextField(
            value = age,
            onValueChange = { age = it
            },
            placeholder = { Text(" Idade") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = modifierTextField
        )
        TextField(
            value = login,
            onValueChange = { login = it },
            placeholder = { Text("Login") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = modifierTextField
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = modifierTextField
        )
        Spacer(modifier = Modifier.height(16.dp))

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
                if (name.isEmpty() || age.isEmpty() || login.isEmpty() || senha.isEmpty()){
                    mensagemErro="Alguns campos não foram preenchidos"

                } else {
                   onRegister()
                    goTo()
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = Color.White
            ),
            modifier = modifierTextField
        ) {
            Text("Registrar-se")
        }
        TextButton(
            modifier= Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFFC31E41)
            ),
            onClick = {goTo() }

        ) {
            Text(
                "Já Registrado? Faça Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultRegisterPreview() {
    val modifierScreen: Modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1A1A1A))
        .padding(16.dp)
    RegisterScreen(modifier = modifierScreen )
}