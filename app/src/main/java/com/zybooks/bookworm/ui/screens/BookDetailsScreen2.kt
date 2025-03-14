package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.zybooks.bookworm.Book
import com.zybooks.bookworm.ui.theme.BookwormTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen2(bookId: Int, navController: NavHostController) {
    BookwormTheme {
        val book = sampleBooks.find { it.id == bookId }

        if (book != null) {
            val booksByAuthor = getBooksByAuthor(sampleBooks, book)
            Scaffold(
                topBar = { BookwormBackHeader(navController) },
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
                                        val starIcon =
                                            if (index < book.userRating) Icons.Filled.Star else Icons.Outlined.Star
                                        Icon(
                                            imageVector = starIcon,
                                            contentDescription = "Star Rating",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    Text(
                                        text = "${book.userRating}/5",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
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

                        BookDetailsInfo(book)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "About the Author",
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = book.authorBio,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        FloatingActionButton(
                            onClick = { navController.navigate("editBook/$bookId") },
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                        {
                            Row(
                                modifier = Modifier.wrapContentWidth()
                                    .padding(start = 5.dp, end = 5.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit Book"
                                )
                                Spacer(modifier = Modifier.height(2.5.dp))
                                Spacer(modifier = Modifier.width(5.dp))
                                Text("Edit details", style = MaterialTheme.typography.labelSmall)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Placeholder for more novels from this author
                        Text(
                            "More Novels from this Author ...",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalScrollableBookRow(booksByAuthor, navController)
                    }
                }
            )
        } else {
            Text("Book not found", style = MaterialTheme.typography.bodyLarge)
        }
    }

}

@Composable
fun HorizontalScrollableBookRow(books: List<Book>, navController: NavHostController) {
    BookwormTheme {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            for (book in books) {
                BookCover(book, navController)
            }
        }
    }
}

@Composable
fun BookCover(book: Book, navController: NavHostController) {
    Column(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = "Book Cover",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate("bookDetails/${book.id}") // Assuming book details screen can be navigated with the book ID
                }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.title,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(120.dp)
        )
    }
}

@Composable
fun BookDetailsInfo(book: Book) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        BookDetailRow(label = "Page Count", value = "${book.totalPages}")
        BookDetailRow(label = "Language", value = "English")
    }
}

@Composable
fun BookDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.width(100.dp),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.width(16.dp)) // Space between label and value

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookwormBackHeader(navController: NavHostController) {
    Box {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // Ensures proper spacing
                    verticalAlignment = Alignment.CenterVertically // Aligns items on the same axis
                ) {
                    Text(
                        "BOOKWORM",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color.Black
                    )

                    IconButton(onClick = {navController.navigate("settings") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings), // Replace with your actual drawable resource for profile icon
                            contentDescription = "Settings",
                            modifier = Modifier.size(45.dp).padding(end = 16.dp) // Match padding to original header
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black // Ensuring visibility against a white background
                    )
                }
            },
//            actions = {
//
//            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .height(64.dp) // Adjust the height if needed
                .padding(bottom = 8.dp) // Adds padding at the bottom to push the AppBar content up
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(380.dp) // Ensures the border spans the entire width of the screen
                .height(1.dp)
                .background(Color.Black)
        )
    }
}


fun getBooksByAuthor(books: List<Book>, currentBook: Book): List<Book> {
    return books.filter { it.author == currentBook.author && it.id != currentBook.id }
}
