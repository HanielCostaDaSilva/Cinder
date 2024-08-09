package com.haniel.cinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haniel.cinder.ui.theme.screens.AuthScreen
import com.haniel.cinder.ui.theme.screens.CinderPrincipalScreen
import com.haniel.cinder.ui.theme.screens.RegisterScreen

class MainActivity : ComponentActivity() {
    private val modifierScreen:Modifier= Modifier.fillMaxSize()
    .background(Color(0xFF1A1A1A))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App() {
        val BackColor= Color(0xFFC31E41)
        val contentColor = Color(0xFFE7E7E7)
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            modifier = modifierScreen,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackColor,
                        titleContentColor = contentColor,
                    ),

                    title = { Text("Cinder") },
                    navigationIcon = {
                        IconButton(
                            onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint=contentColor)
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(imageVector = Icons.Default.Person,
                                contentDescription = "Account",
                                tint= contentColor)
                        }
                    }
                )
            }
        ) { paddingValues ->
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "auth_screen") {
                composable("auth_screen"){
                    AuthScreen(modifier= modifierScreen.padding(paddingValues),
                        onSignInClick = {
                        navController.navigate("principal_screen")
                    },
                        ifNewGoTo = {
                            navController.navigate("register_screen")
                        }
                    )
                }
                composable("principal_screen"){
                    CinderPrincipalScreen(modifier=modifierScreen.padding(paddingValues))
                }

                composable("register_screen"){
                    RegisterScreen(modifier= modifierScreen.padding(paddingValues),
                        goTo = {
                            navController.navigate("principal_screen")
                        })
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainActivityPreview() {
        App()
    }
}
