package com.zybooks.bookworm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BookDetailsScreen(bookId: Int, navController: NavHostController) {
    val book = sampleBooks.find { it.id == bookId }

    if (book != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = book.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Author: ${book.author}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Book not found", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Home")
            }
        }
    }
}
