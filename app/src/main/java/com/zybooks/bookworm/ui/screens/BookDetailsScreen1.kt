package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zybooks.bookworm.sampleBooks
import com.zybooks.bookworm.storage.BookStorageManager
import com.zybooks.bookworm.ui.theme.BookwormTheme
import com.zybooks.bookworm.ui.viewmodel.ThemeViewModel

@Composable
fun BookDetailsScreen1(
    bookId: Int,
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {
    BookwormTheme (themeViewModel = themeViewModel){
        val context = LocalContext.current
        // Load the books from file (including any changes made)
        val books = BookStorageManager.loadBooks(context)
        // Find the book with the matching ID.
        val book = books.find { it.id == bookId }

        if (book != null) {
            Scaffold(
                topBar = { BookwormBackHeader(navController) },  // Using the BookwormHeader
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = book.imageUrl,
                            contentDescription = "Book cover",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = book.author,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            repeat(5) { index ->
                                val starIcon =
                                    if (index < book.userRating) Icons.Filled.Star else Icons.Outlined.Star
                                Icon(
                                    imageVector = starIcon,
                                    contentDescription = "Star Rating",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${"%.2f".format(book.userRating)}/5", fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = book.userReview,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        val progressFraction = if (book.totalPages > 0) {
                            book.pagesRead.toFloat() / book.totalPages.toFloat()
                        } else {
                            0f
                        }
                        Text(
                            text = "Progress: ${book.pagesRead} / ${book.totalPages}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            LinearProgressIndicator(
                                progress = { progressFraction },
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(16.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .clip(RoundedCornerShape(4.dp)),
                                color = MaterialTheme.colorScheme.onBackground,
                                trackColor = MaterialTheme.colorScheme.background,
                            )

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { navController.navigate("BookDetailsScreen2/$bookId") },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.wrapContentWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary, // Set the background color of the button
                                contentColor = MaterialTheme.colorScheme.primary // Set the color of the text and icon
                            )
                        ) {
                            Text(
                                text = "Show More",
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Arrow"
                            )
                        }
                    }
                }
            )
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp).background(MaterialTheme.colorScheme.background)) {
                Text(
                    "Book not found",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
