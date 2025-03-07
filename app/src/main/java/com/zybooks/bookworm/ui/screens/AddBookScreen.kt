package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zybooks.bookworm.Book
import com.zybooks.bookworm.R
import com.zybooks.bookworm.sampleBooks
import kotlin.math.roundToInt
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(navController: NavHostController) {
    // State to hold the input values
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var userRating by remember { mutableFloatStateOf(0f) }
    var review by remember { mutableStateOf("") } // Review input
    var pagesRead by remember { mutableIntStateOf(0) } // Pages read input
    var totalPages by remember { mutableIntStateOf(0) } // Example total pages for the book

    fun addBook() {
        if (title.isNotEmpty() && author.isNotEmpty() && imageUrl.isNotEmpty()) {
            // Add the new book to the list (you might need to update your `sampleBooks` list here)
            val newBook = Book(
                id = sampleBooks.size,  // Assuming unique ID is based on the list size
                title = title,
                author = author,
                imageUrl = R.drawable.placeholder_cover, // Convert string to a valid resource ID if needed
                userRating = userRating,
                dateAdded = Calendar.getInstance().time.toString(), // For example, use the current date
                review = review, // Add review
                totalPages = totalPages,
                pagesRead = pagesRead // Add pages read
            )
            sampleBooks.add(newBook)  // Update the list with the new book
            navController.navigate("bookDetails/${newBook.id}")  // Navigate to the book's details page
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add a Book") })
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                // Title Input
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                // Author Input
                TextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                // Image URL Input
                TextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                // Rating Input (Slider for better experience)
                Slider(
                    value = userRating,
                    onValueChange = { userRating = (it * 10).roundToInt() / 10f }, // Round to 1 decimal place
                    valueRange = 0f..5f,
                    steps = 50, // 50 steps for 0.1 increments
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = "Rating: $userRating",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                // Review Input (TextField)
                Text(
                    text = "Your Review:",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                TextField(
                    value = review,
                    onValueChange = { review = it },
                    label = { Text("Enter your review...") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    maxLines = 3
                )

                // Total Pages
                Text(
                    text ="How many pages does this book have?",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                TextField(
                    value = totalPages.toString(),
                    onValueChange = { totalPages = it.toIntOrNull() ?: 0 }, // Validate input as integer
                    label = { Text("Total Pages") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                // Pages Read Input (Slider)
                Text(
                    text = "Pages Read: $pagesRead / $totalPages",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Slider(
                    value = pagesRead.toFloat(),
                    onValueChange = { pagesRead = it.roundToInt() },
                    valueRange = 0f..totalPages.toFloat(),
                    steps = if (totalPages == 0) 0 else totalPages - 1, // for a total of `totalPages` discrete steps
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Submit Button
                Button(
                    onClick = { addBook() },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Add Book")
                }
            }
        }
    )
}
