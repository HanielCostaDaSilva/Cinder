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

        // Função de navegação reutilizável
        fun navigateToScreen(route: String) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = false }
            }
        }

        NavHost(navController = navController, startDestination = "first_login") {
            composable("auth_screen") {
                AuthScreen(
                    modifier = modifierScreen,
                    navController = navController,
                    onSignInClick = { user: User ->
                        if (user.interests.isEmpty()) {
                            // Se o usuário não tiver interesses cadastrados, redireciona para a tela de interesses
                            navigateToScreen("interestsScreen")
                        } else {
                            // Se já tiver interesses, vai para a tela principal
                            navigateToScreen("principal_screen")
                        }
                    },
                    ifNewGoTo = {
                        navigateToScreen("register_screen")
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
                    onProfile = { navigateToScreen("profileUser") },
                    onChatClick = { navigateToScreen("chatScreen") },
                    onHomeClick = { navigateToScreen("principal_screen") },
                    onMatchesClick = { navigateToScreen("matches_screen") }
                )
            }

            composable("register_screen") {
                RegisterScreen(
                    modifier = modifierScreen,
                    onRegister = {
                        navigateToScreen("auth_screen")
                    },
                    goToLogin = {
                        navigateToScreen("auth_screen")
                    })
            }

            composable("first_login") {
                FirstLogin(
                    modifier = modifierScreen,
                    onSignInClick = {
                        navigateToScreen("auth_screen")
                    }, onNavigateToCadastro = {
                        navigateToScreen("register_screen")
                    }
                )
            }

            composable("profileUser") {
                ProfileUserScreen(
                    navigateToEdition = { navigateToScreen("editionScreen") },
                    navigateToLogin = { navigateToScreen("auth_screen") },
                    navigateToHome = { navigateToScreen("principal_screen") },
                    onChatClick = { navigateToScreen("userSelectionScreen") },
                    onProfileClick = {} ,
                    onMatchesClick = { navigateToScreen("matches_screen") }
                )
            }

            composable("editionScreen") {
                EditionScreen(
                    modifier = modifierScreen,
                    onBack = {  navigateToScreen("profileUser") }
                )
            }

            composable("matches_screen") {
                MatchesScreen(
                    currentUser = usuarioLogado,
                    modifier = modifierScreen ,
                    onHomeClick = { navigateToScreen("principal_screen") },
                    onChatClick = { navigateToScreen("userSelectionScreen") },
                    onProfile = { navigateToScreen("profileUser") },
                    onMatchesClick = { navigateToScreen("matches_screen") }
                )
            }

            composable("userSelectionScreen") {
                val users = listOf(
                    User(id = "1", name = "karla", imageID = R.drawable.karla_fernades),
                    User(id = "2", name = "Mary", imageID = R.drawable.mary_lopez)
                )

                UserSelectionScreen(
                    users = users,
                    onUserSelected = { user ->
                        userViewModel.selectedUser = user
                        navController.navigate("chatScreen") {
                            popUpTo("userSelectionScreen") { inclusive = false }
                        }
                    },
                    onHomeClick = { navigateToScreen("principal_screen") },
                    onChatClick = { navigateToScreen("userSelectionScreen") },
                    onProfile = { navigateToScreen("profileUser") },
                    onMatchesClick = { navigateToScreen("matches_screen") }
                )
            }

            composable("chatScreen") {
                val chatViewModel: ChatViewModel = viewModel()
                val selectedUser = userViewModel.selectedUser

                if (selectedUser != null) {
                    ChatScreen(viewModel = chatViewModel, user = selectedUser)
                } else {
                    navigateToScreen("userSelectionScreen")
                }
            }
        }
    }
}
