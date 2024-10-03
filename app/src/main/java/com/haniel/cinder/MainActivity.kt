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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haniel.cinder.model.User
import com.haniel.cinder.ui.theme.screens.*
import com.haniel.cinder.viewmodel.ChatViewModel
import com.haniel.cinder.viewmodel.UserViewModel

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
        val userViewModel: UserViewModel = viewModel()

        NavHost(navController = navController, startDestination = "first_login") {
            composable("auth_screen") {
                AuthScreen(
                    modifier = modifierScreen,
                    navController = navController,
                    onSignInClick = { user: User ->
                        if (user.interests.isEmpty()) {
                            // Se o usuário não tiver interesses cadastrados, redireciona para a tela de interesses
                            navController.navigate("interestsScreen")
                        } else {
                            // Se já tiver interesses, vai para a tela principal
                            navController.navigate("principal_screen")
                        }
                    },
                    ifNewGoTo = {
                        navController.navigate("register_screen")
                    }
                )
            }

            composable("interestsScreen") {
                InterestsScreen(
                    navController = navController,
                    onBack = { navController.popBackStack() },
                    onSave = {
                        // Após salvar os interesses, navega para a tela principal
                        navController.navigate("principal_screen") {
                            popUpTo("interestsScreen") { inclusive = true }
                        }
                    }
                )
            }

            composable("principal_screen") {
                CinderPrincipalScreen(
                    modifier = modifierScreen,
                    onProfile = { navController.navigate("profileUser") },
                    onChatClick = { navController.navigate("chatScreen") },
                    onHomeClick = { navController.navigate("principal_screen") }
                )
            }

            composable("register_screen") {
                RegisterScreen(
                    modifier = modifierScreen,
                    onRegister = {
                        navController.navigate("auth_screen")
                    },
                    goToLogin = {
                        navController.navigate("auth_screen")
                    }
                )
            }

            composable("first_login") {
                FirstLogin(
                    modifier = modifierScreen,
                    onSignInClick = {
                        navController.navigate("auth_screen")
                    },
                    onNavigateToCadastro = {
                        navController.navigate("register_screen")
                    }
                )
            }

            composable("profileUser") {
                ProfileUserScreen(
                    navigateToEdition = { navController.navigate("editionScreen") },
                    navigateToLogin = { navController.navigate("auth_screen") },
                    navigateToHome = { navController.navigate("principal_screen") },
                    onChatClick = { navController.navigate("userSelectionScreen") },
                    onProfileClick = {}
                )
            }

            composable("editionScreen") {
                EditionScreen(
                    modifier = modifierScreen,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("userSelectionScreen") {
                val users = listOf(
                    User(id = "1", name = "joaquim", imageID = R.drawable.cinder),
                    User(id = "2", name = "Leticia", imageID = R.drawable.cinder)
                )

                UserSelectionScreen(
                    users = users,
                    onUserSelected = { user ->
                        userViewModel.selectedUser = user
                        navController.navigate("chatScreen") {
                            popUpTo("userSelectionScreen") { inclusive = false }
                        }
                    },
                    onHomeClick = { navController.navigate("principal_screen") },
                    onChatClick = { navController.navigate("userSelectionScreen") },
                    onProfile = { navController.navigate("profileUser") }
                )
            }

            composable("chatScreen") {
                val chatViewModel: ChatViewModel = viewModel()
                val selectedUser = userViewModel.selectedUser

                if (selectedUser != null) {
                    ChatScreen(viewModel = chatViewModel, user = selectedUser)
                } else {
                    navController.navigate("userSelectionScreen") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
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
