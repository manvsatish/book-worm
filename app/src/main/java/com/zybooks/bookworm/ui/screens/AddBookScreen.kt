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
import kotlin.math.roundToInt
import java.util.Calendar
import kotlin.math.roundToInt
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import com.zybooks.bookworm.storage.BookStorageManager
import androidx.compose.ui.graphics.Color
import com.zybooks.bookworm.ui.theme.BookwormTheme
import com.zybooks.bookworm.ui.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(navController: NavHostController, themeViewModel: ThemeViewModel, books: MutableList<Book>) {
    val context = LocalContext.current  // Get the current context using Compose
    BookwormTheme(themeViewModel = themeViewModel) {
        // State to hold the input values
        var title by remember { mutableStateOf("") }
        var author by remember { mutableStateOf("") }
        var imageUrl by remember { mutableStateOf("") }
        var userRating by remember { mutableFloatStateOf(0f) }
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
                    totalPages = totalPages,
                    pagesRead = pagesRead,
                    genre = genre,
                    description = description,
                    authorBio = authorBio,
                    userReview = userReview
                )
                books.add(newBook)
                BookStorageManager.saveBooks(context, books)

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
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = author,
                            onValueChange = { author = it },
                            label = { Text("Author") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = imageUrl,
                            onValueChange = { imageUrl = it },
                            label = { Text("Image URL") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    item {
                        Slider(
                            value = userRating,
                            onValueChange = { userRating = (it * 10).roundToInt() / 10f },
                            valueRange = 0f..5f,
                            steps = 50,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.onPrimary, // Color for the thumb (draggable part)
                                activeTrackColor = MaterialTheme.colorScheme.onPrimary, // Color for the active portion of the track
                                inactiveTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f) // Color for the inactive portion of the track
                            )
                        )
                        Text(
                            text = "Rating: $userRating",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                    item {
                        Text(
                            text = "Your Review:",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        OutlinedTextField(
                            value = userReview,
                            onValueChange = { userReview = it },
                            label = { Text("Enter your review...") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            maxLines = 3,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    item {
                        Text(
                            text = "How many pages does this book have?",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        OutlinedTextField(
                            value = totalPages.toString(),
                            onValueChange = { totalPages = it.toIntOrNull() ?: 0 },
                            label = { Text("Total Pages") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    item {
                        Text(
                            text = "Pages Read: $pagesRead / $totalPages",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Slider(
                            value = pagesRead.toFloat(),
                            onValueChange = { pagesRead = it.roundToInt() },
                            valueRange = 0f..totalPages.toFloat(),
                            steps = if (totalPages == 0) 0 else totalPages - 1,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.onPrimary, // Color for the thumb (draggable part)
                                activeTrackColor = MaterialTheme.colorScheme.onPrimary, // Color for the active portion of the track
                                inactiveTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f) // Color for the inactive portion of the track
                            )
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = genre,
                            onValueChange = { genre = it },
                            label = { Text("Genre") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = authorBio,
                            onValueChange = { authorBio = it },
                            label = { Text("Author Bio") },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    item {
                        OutlinedButton(
                            onClick = { addBook() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp), // Reduced vertical padding
                            shape = RoundedCornerShape(8.dp), // Less rounded corners
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Add Book", style = MaterialTheme.typography.labelSmall)
                        }
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancel", style = MaterialTheme.typography.labelSmall)
                        }
                    }

                }
            }
        )
    }
}
