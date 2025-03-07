package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zybooks.bookworm.R
import com.zybooks.bookworm.sampleBooks
import androidx.compose.material3.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen2(bookId: Int, navController: NavHostController) {
    val book = sampleBooks.find { it.id == bookId }

    if (book != null) {
        Scaffold(
            topBar = { BookwormHeader(navController) },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = book.imageUrl,
                            contentDescription = "Book cover",
                            modifier = Modifier
                                .size(height = 250.dp, width = 175.dp)
                                .padding(start = 8.dp)
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = book.author,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.height(8.dp))

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
                                Text(text = "${book.userRating}/5", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Genre | ${book.genre}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)  // Adjust this value to suit your design
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = book.description,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Page Count: ${book.totalPages}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Text(
                        text = "Language: English",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )


                    Text(
                        text = "ISBN: ${book.isbn}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Text(
                        text = "Published ${book.publishDate}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "About the Author",
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = book.authorBio,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Placeholder for more novels from this author
                    Text("More Novels from this Author ...",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(start = 8.dp))

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    } else {
        Text("Book not found", style = MaterialTheme.typography.bodyLarge)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookwormHeader(navController: NavHostController) {
    Box {
        TopAppBar(
            title = {
                Text(
                    "BOOKWORM",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black  // Ensuring the icon is visible against the background
                    )
                }
            },
            actions = {
                // Profile Icon Button
                IconButton(onClick = { /* Handle profile icon click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.userprofile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(45.dp).padding(bottom = 12.dp, end = 4.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            modifier = Modifier.height(64.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(376.dp)
                .height(2.dp)
                .background(Color.Black)
        )
    }
}
