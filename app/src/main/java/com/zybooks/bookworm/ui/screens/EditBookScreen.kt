package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import com.zybooks.bookworm.sampleBooks
import com.zybooks.bookworm.ui.theme.BookwormTheme
import com.zybooks.bookworm.ui.viewmodel.ThemeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(bookId: Int, navController: NavHostController, themeViewModel: ThemeViewModel) {
    BookwormTheme(themeViewModel = themeViewModel) {
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
                        steps = 50,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.onPrimary, // Color for the thumb (draggable part)
                            activeTrackColor = MaterialTheme.colorScheme.onPrimary, // Color for the active portion of the track
                            inactiveTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f) // Color for the inactive portion of the track
                        )
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
                        OutlinedButton(
                            onClick = {
                            // Handle save logic (Update the book details)
                                book?.let {
                                    it.title = title
                                    it.author = author
                                    it.userRating = rating
                                    it.imageUrl = imageUrl
                                }
                                navController.popBackStack() },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Save")
                        }
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Red, // Neutral background color for "Cancel"
                                contentColor = Color.White // Red color for "Cancel" text
                            )
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        )
    }
}