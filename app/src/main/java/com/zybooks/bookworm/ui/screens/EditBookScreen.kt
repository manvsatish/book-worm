package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zybooks.bookworm.Book
import com.zybooks.bookworm.storage.BookStorageManager
import com.zybooks.bookworm.ui.theme.BookwormTheme
import com.zybooks.bookworm.ui.viewmodel.ThemeViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(
    bookId: Int,
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    books: MutableList<Book>
) {
    val context = LocalContext.current
    BookwormTheme(themeViewModel = themeViewModel) {
        // Look for the book in the passed-in list.
        val book = books.find { it.id == bookId }

        if (book != null) {
            // Initialize state values with the book's current values.
            var title by remember { mutableStateOf(book.title) }
            var author by remember { mutableStateOf(book.author) }
            var rating by remember { mutableFloatStateOf(book.userRating) }
            var imageUrl by remember { mutableStateOf(book.imageUrl) }
            var totalPages by remember { mutableIntStateOf(book.totalPages) }
            var pagesRead by remember { mutableIntStateOf(book.pagesRead) }
            var genre by remember { mutableStateOf(book.genre) }
            var description by remember { mutableStateOf(book.description) }
            var authorBio by remember { mutableStateOf(book.authorBio) }
            var userReview by remember { mutableStateOf(book.userReview) }

            fun updateBook() {
                // Update the book's fields.
                book.title = title
                book.author = author
                book.userRating = rating
                book.imageUrl = imageUrl
                book.totalPages = totalPages
                book.pagesRead = pagesRead
                book.genre = genre
                book.description = description
                book.authorBio = authorBio
                book.userReview = userReview

                // Save the updated list.
                BookStorageManager.saveBooks(context, books)

                // Return to the previous screen.
                navController.popBackStack()
            }

            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Edit Book") })
                },
                content = { padding ->
                    // Wrap the content in a verticalScroll so it can be scrolled.
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = author,
                            onValueChange = { author = it },
                            label = { Text("Author") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = imageUrl,
                            onValueChange = { imageUrl = it },
                            label = { Text("Image URL") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        Text("Rating", modifier = Modifier.padding(8.dp))
                        Slider(
                            value = rating,
                            onValueChange = { rating = it },
                            valueRange = 0f..5f,
                            steps = 50,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.onPrimary,
                                activeTrackColor = MaterialTheme.colorScheme.onPrimary,
                                inactiveTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = totalPages.toString(),
                            onValueChange = { totalPages = it.toIntOrNull() ?: 0 },
                            label = { Text("Total Pages") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        Text("Pages Read: $pagesRead / $totalPages", modifier = Modifier.padding(8.dp))
                        Slider(
                            value = pagesRead.toFloat(),
                            onValueChange = { pagesRead = it.roundToInt() },
                            valueRange = 0f..totalPages.toFloat(),
                            steps = if (totalPages == 0) 0 else totalPages - 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.onPrimary,
                                activeTrackColor = MaterialTheme.colorScheme.onPrimary,
                                inactiveTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = genre,
                            onValueChange = { genre = it },
                            label = { Text("Genre") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = authorBio,
                            onValueChange = { authorBio = it },
                            label = { Text("Author Bio") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                        OutlinedTextField(
                            value = userReview,
                            onValueChange = { userReview = it },
                            label = { Text("User Review") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp), // reduced vertical padding
                            horizontalArrangement = Arrangement.spacedBy(4.dp) // space between the buttons
                        ) {
                            OutlinedButton(
                                onClick = { updateBook() },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp), // less rounded
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimary,
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Save", style = MaterialTheme.typography.labelSmall)
                            }
                            OutlinedButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp), // less rounded
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
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    "Book not found",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
