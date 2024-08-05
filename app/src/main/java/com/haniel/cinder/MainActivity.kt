package com.haniel.cinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.haniel.cinder.R
import com.haniel.cinder.model.Person
import com.haniel.cinder.model.UserRepository

class MainActivity : ComponentActivity() {
    private val userRepository = UserRepository();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinderPrincipalScreen()
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CinderPrincipalScreen() {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),

                    title = { Text("Cinder") },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Person, contentDescription = "Account")
                        }
                    }
                )
            }
        ) { paddingValues ->
            MainContent(modifier = Modifier.padding(paddingValues))
        }
    }

    @Composable
    fun PersonCard(person: Person) {
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
                    painter = painterResource(person.imageID),
                    contentDescription = "${person.name} Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(30.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Name: ${person.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Age: ${person.age}", fontSize = 18.sp)
            }

        }
    }

    @Composable
    fun BiograpyCard(person: Person) {
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
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    "Biography",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = person.biograpy,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun FavoritePersonButton(person: Person, modifier:Modifier) {
        var favoriteIcon by remember { mutableStateOf(Icons.Filled.FavoriteBorder) }
        val favoriteIconFilled = Icons.Filled.FavoriteBorder
        val favoriteIconBorder = Icons.Filled.Favorite

        SmallFloatingActionButton(
            modifier = modifier,

                    onClick = {
                favoriteIcon = if (favoriteIcon == favoriteIconBorder) {
                    favoriteIconFilled
                } else {
                    favoriteIconBorder
                }
                println("O usuário favoritou: ${person.name}")
            },
            shape = CircleShape,
            containerColor = Color.White,
            contentColor = Color.Red

        ) {
            Icon(favoriteIcon, contentDescription = "Large floating action button")
        }
    }


    @Composable
    fun MainContent(modifier: Modifier = Modifier) {
        var indexPerson by remember { mutableIntStateOf(0) }
        val personDisplay by remember { derivedStateOf { userRepository.getPerson(indexPerson) } }
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ){
            Column(
            ) {
                PersonCard(personDisplay)
                Spacer(modifier = Modifier.height(16.dp))
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
                            indexPerson = (indexPerson + 1) % userRepository.size()
                        },
                    ) {
                        Text("Match")
                    }
                    Button(
                        modifier = Modifier.width(150.dp),
                        onClick = {
                            indexPerson =
                                if (indexPerson - 1 < 0) userRepository.size() - 1 // Caso Especial: O primeiro item da lista
                                else (indexPerson - 1) % userRepository.size() // Caso Base

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Next")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                BiograpyCard(person = personDisplay)
            }
            FavoritePersonButton(person = personDisplay,Modifier.align(Alignment.TopEnd).padding(16.dp))

        }

    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CinderPrincipalScreen()
    }
}