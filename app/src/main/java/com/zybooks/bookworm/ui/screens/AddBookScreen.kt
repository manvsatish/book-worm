package com.zybooks.bookworm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add a Book") })
        },
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                Text("Add Book Page", modifier = Modifier.padding(16.dp))
            }
        }
    )
}
