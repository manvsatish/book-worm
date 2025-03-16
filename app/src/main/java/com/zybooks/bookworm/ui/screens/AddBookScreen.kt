package com.zybooks.bookworm.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zybooks.bookworm.Book
import com.zybooks.bookworm.ui.theme.BookwormTheme
import java.util.Calendar
import kotlin.math.roundToInt
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.LocalContext
import com.zybooks.bookworm.storage.BookStorageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(navController: NavHostController) {
    val context = LocalContext.current  // Get the current context using Compose
    BookwormTheme {
        var title by remember { mutableStateOf("") }
        var author by remember { mutableStateOf("") }
        var imageUrl by remember { mutableStateOf("") }
        var userRating by remember { mutableFloatStateOf(0f) }
        var review by remember { mutableStateOf("") }
        var pagesRead by remember { mutableIntStateOf(0) }
        var totalPages by remember { mutableIntStateOf(0) }
        var genre by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var authorBio by remember { mutableStateOf("") }
        var userReview by remember { mutableStateOf("") }

        fun addBook() {
            if (title.isNotEmpty() && author.isNotEmpty() && imageUrl.isNotEmpty()) {
                val newBook = Book(
                    id = BookStorageManager.loadBooks(context).size,
                    title = title,
                    author = author,
                    imageUrl = imageUrl,
                    userRating = userRating,
                    dateAdded = Calendar.getInstance().time.toString(),
                    review = review,
                    totalPages = totalPages,
                    pagesRead = pagesRead,
                    genre = genre,
                    description = description,
                    authorBio = authorBio,
                    userReview = userReview
                )
                val currentBooks = BookStorageManager.loadBooks(context)
                currentBooks.add(newBook)
                BookStorageManager.saveBooks(context, currentBooks)

                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            } else {
                Log.d("AddBookScreen", "Required fields are empty")
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Add a Book") })
            },
            content = { padding ->
                LazyColumn(modifier = Modifier.padding(padding)) {
                    item {
                        TextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }
                    item {
                        TextField(
                            value = author,
                            onValueChange = { author = it },
                            label = { Text("Author") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }
                    item {
                        TextField(
                            value = imageUrl,
                            onValueChange = { imageUrl = it },
                            label = { Text("Image URL") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }
                    item {
                        Slider(
                            value = userRating,
                            onValueChange = { userRating = (it * 10).roundToInt() / 10f },
                            valueRange = 0f..5f,
                            steps = 50,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = "Rating: $userRating",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                    item {
                        TextField(
                            value = review,
                            onValueChange = { review = it },
                            label = { Text("Review") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            maxLines = 3
                        )
                    }
                    item {
                        TextField(
                            value = totalPages.toString(),
                            onValueChange = { totalPages = it.toIntOrNull() ?: 0 },
                            label = { Text("Total Pages") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }
                    item {
                        Slider(
                            value = pagesRead.toFloat(),
                            onValueChange = { pagesRead = it.roundToInt() },
                            valueRange = 0f..totalPages.toFloat(),
                            steps = if (totalPages == 0) 0 else totalPages - 1,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                    item {
                        TextField(
                            value = genre,
                            onValueChange = { genre = it },
                            label = { Text("Genre") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                        TextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                        TextField(
                            value = authorBio,
                            onValueChange = { authorBio = it },
                            label = { Text("Author Bio") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }
                    item {
                        Button(
                            onClick = { addBook() },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Text("Add Book")
                        }
                    }
                }
            }
        )
    }
}
