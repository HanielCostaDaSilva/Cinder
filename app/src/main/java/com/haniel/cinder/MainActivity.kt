package com.haniel.cinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haniel.cinder.model.User
import com.haniel.cinder.ui.theme.screens.AuthScreen
import com.haniel.cinder.ui.theme.screens.CinderPrincipalScreen
import com.haniel.cinder.ui.theme.screens.FirstLogin
import com.haniel.cinder.ui.theme.screens.RegisterScreen

class MainActivity : ComponentActivity() {
    private val modifierScreen: Modifier = Modifier
        .fillMaxSize()
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
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "first_login") {
            composable("auth_screen") {
                AuthScreen(modifier = modifierScreen,
                    onSignInClick = { user: User ->
                        print("Hello ${user.name}")
                        navController.navigate("principal_screen")
                    },
                    ifNewGoTo = {
                        navController.navigate("register_screen")
                    }
                )
            }
            composable("principal_screen") {
                CinderPrincipalScreen(modifier = modifierScreen)
            }

            composable("register_screen") {
                RegisterScreen(modifier = modifierScreen,
                    onRegister = {
                        navController.navigate("auth_screen")
                    },
                    goToLogin = {
                        navController.navigate("auth_screen")
                    })
            }
            composable("first_login") {
                FirstLogin(modifier = modifierScreen,
                    onSignInClick = {
                        navController.navigate("auth_screen")
                    }, onNavigateToCadastro = {
                        navController.navigate("register_screen")
                    }
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun MainActivityPreview() {
        App()
    }
}