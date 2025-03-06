package com.zybooks.bookworm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zybooks.bookworm.ui.theme.BookwormTheme
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class) // Opt-in to experimental Material3 API
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookwormApp()
        }
    }
}

@Composable
fun BookwormApp() {
    // Sort books by rating, and then by date (both descending)
    val sortedBooks = sampleBooks.sortedWith(compareByDescending<Book> { it.userRating }
        .thenByDescending { it.dateAdded })

    BookwormTheme {
        Scaffold(
            topBar = {
                BookwormHeader()
            },
            floatingActionButton = {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    FloatingActionButton(onClick = { /* TODO: Add book action */ }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    FloatingActionButton(onClick = { /* TODO: Edit book action */ }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Book")
                    }
                }
            },
            content = { paddingValues ->
                BookGrid(books = sortedBooks, modifier = Modifier.padding(paddingValues))
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookwormHeader() {
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            modifier = Modifier.height(64.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(376.dp)
                .height(1.dp)
                .background(Color.Black)
        )
    }
}

@Composable
fun BookGrid(books: List<Book>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(books) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 107.dp, height = 160.dp)
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder_cover),
                contentDescription = "Book Cover",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = book.title,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookwormApp()
}

// Sample data
val sampleBooks = listOf(
    Book(title = "1984", author = "George Orwell", imageUrl = "", userRating = 1.1f, dateAdded = "2024-10-01"),
    Book(title = "Brave New World", author = "Aldous Huxley", imageUrl = "", userRating = 0.9f, dateAdded = "2024-10-02"),
    Book(title = "To Kill a Mockingbird", author = "Harper Lee", imageUrl = "", userRating = 4.9f, dateAdded = "2024-10-03"),
    Book(title = "The Great Gatsby", author = "F. Scott Fitzgerald", imageUrl = "", userRating = 4.7f, dateAdded = "2024-10-04"),
    Book(title = "Pride and Prejudice", author = "Jane Austen", imageUrl = "", userRating = 3.0f, dateAdded = "2024-03-05"),
    Book(title = "The Hobbit", author = "J.R.R. Tolkien", imageUrl = "", userRating = 2.0f, dateAdded = "2024-10-06"),
    Book(title = "Harry Potter and the Sorcerer's Stone", author = "J.K. Rowling", imageUrl = "", userRating = 5.0f, dateAdded = "2024-02-01"),
    Book(title = "Harry Potter and the Chamber of Secrets", author = "J.K. Rowling", imageUrl = "", userRating = 5.0f, dateAdded = "2024-10-03"),
    Book(title = "Harry Potter and the Prisoner of Azkaban", author = "J.K. Rowling", imageUrl = "", userRating = 5.0f, dateAdded = "2024-09-01"),
    Book(title = "Harry Potter and the Goblet of Fire", author = "J.K. Rowling", imageUrl = "", userRating = 4.8f, dateAdded = "2024-10-01"),
    Book(title = "Harry Potter and the Order of the Phoenix", author = "J.K. Rowling", imageUrl = "", userRating = 4.8f, dateAdded = "2024-10-01"),
    Book(title = "Harry Potter and the Half-Blood Prince", author = "J.K. Rowling", imageUrl = "", userRating = 4.9f, dateAdded = "2024-10-11"),
    Book(title = "Harry Potter and the Deathly Hallows", author = "J.K. Rowling", imageUrl = "", userRating = 4.0f, dateAdded = "2024-08-01"),
    Book(title = "Adventures of Huckleberry Finn", author = "Mark Twain", imageUrl = "", userRating = 4.5f, dateAdded = "2024-05-01"),
    Book(title = "The Fault in Our Stars", author = "John Green", imageUrl = "", userRating = 2.7f, dateAdded = "2024-04-09"),
    Book(title = "Anna Karenina", author = "Leo Tolstoy", imageUrl = "", userRating = 1.0f, dateAdded = "2024-10-14")
)

// Book data class
data class Book(
    val title: String,
    val author: String,
    val imageUrl: String,
    var userRating: Float,
    var dateAdded: String
)
