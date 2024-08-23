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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haniel.cinder.R

@Composable
fun FirstLogin(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onNavigateToCadastro: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background).padding(0.dp,0.dp,0.dp,50.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val imagem = painterResource(id = R.drawable.image_logo)
        Image(
            painter = imagem,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Conecte-se\ne descubra\nnovas",
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Text(
            text = "conexões",
            fontSize = 45.sp,
            color = Color(0xFF5A028F),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { onSignInClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7A0180),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 10.dp)
                .height(50.dp)
        ) {
            Text(text = "FAZER LOGIN", fontWeight = FontWeight.Bold)
        }

        Text(text = "Ou faça login usando", fontWeight = FontWeight.Light)

        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Email",
                tint = Color(0xFF5A028F),
                modifier = Modifier.size(30.dp)
            )
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Telefone",
                tint = Color(0xFF5A028F),
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = "NÃO TEM CONTA?",
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )

        ClickableText(
            text = AnnotatedString("CADASTRE-SE AGORA."),
            onClick = { onNavigateToCadastro() },
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FirstLogin(
        onSignInClick = {},
        onNavigateToCadastro = { }
    )
}