package com.zybooks.bookworm.ui.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zybooks.bookworm.sampleBooks

@Composable
fun BookDetailsScreen1(bookId: Int, navController: NavHostController) {
    val book = sampleBooks.find { it.id == bookId }

    if (book != null) {
        Scaffold(
            topBar = { BookwormHeader(navController) },  // Using the BookwormHeader
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
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
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Author: ${book.author}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { index ->
                            val starIcon = if (index < book.userRating) Icons.Filled.Star else Icons.Outlined.Star
                            Icon(
                                imageVector = starIcon,
                                contentDescription = "Star Rating",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${book.userRating}/5", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Show More Button
                    Button(
                        onClick = { navController.navigate("BookDetailsScreen2/$bookId") },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Show More")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow")
                    }
                }
            }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Book not found", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
