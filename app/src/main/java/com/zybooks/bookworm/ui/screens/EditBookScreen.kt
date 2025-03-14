package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.OutlinedTextField
import com.zybooks.bookworm.sampleBooks
import com.zybooks.bookworm.ui.theme.BookwormTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(bookId: Int, navController: NavHostController) {
    BookwormTheme {
        val book = sampleBooks.find { it.id == bookId }

        var title by remember { mutableStateOf(book?.title ?: "") }
        var author by remember { mutableStateOf(book?.author ?: "") }
        var rating by remember { mutableFloatStateOf(book?.userRating ?: 3f) } // Default to 3 stars
        var imageUrl by remember { mutableStateOf(book?.imageUrl ?: "") }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Edit Book") })
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    Text(text = "Title", style = MaterialTheme.typography.labelLarge)
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Author", style = MaterialTheme.typography.labelLarge)
                    OutlinedTextField(
                        value = author,
                        onValueChange = { author = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Rating", style = MaterialTheme.typography.labelLarge)
                    Slider(
                        value = rating,
                        onValueChange = { rating = it },
                        valueRange = 0f..5f,
                        steps = 4,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    //need to add option to upload image
                    Text(text = "Image URL", style = MaterialTheme.typography.labelLarge)
                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            // Handle save logic (Update the book details)
                            book?.let {
                                it.title = title
                                it.author = author
                                it.userRating = rating
                                it.imageUrl = imageUrl
                            }
                            navController.popBackStack()
                        }) {
                            Text("Save")
                        }
                        OutlinedButton(onClick = { navController.popBackStack() }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        )
    }
}
