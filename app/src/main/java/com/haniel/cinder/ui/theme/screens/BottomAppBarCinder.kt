package com.haniel.cinder.ui.theme.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomAppBarCinder(
    onHomeClick: () -> Unit,
    onChatClick: () -> Unit,
    onProfile: () -> Unit,
    modifier: Modifier
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        IconButton(onClick = onProfile, Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile"
            )
        }
        Spacer(modifier = Modifier.weight(1f, true))
        IconButton(onClick = onHomeClick, Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home"
            )
        }
        Spacer(modifier = Modifier.weight(1f, true))
        IconButton(onClick = onChatClick, Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "Chat"
            )
        }
    }
}