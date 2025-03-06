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
    val sortedBooks = sampleBooks.sortedWith(
        compareByDescending<Book> { it.userRating }
            .thenByDescending { it.dateAdded }
    )
    val topBooks = sortedBooks.take(3) // Take the top 3 books

    BookwormTheme {
        Scaffold(
            topBar = {
                BookwormHeader()
            },
            floatingActionButton = {
                FloatingActionButtons()
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    Spacer(modifier = Modifier.height(20.dp)) // Ensure there is space after the top bar
                    TopBooksHeader(topBooks = topBooks)  // Display top books in a special header
                    Spacer(modifier = Modifier.height(10.dp))  // Control space between top books and grid
                    BookGrid(books = sortedBooks) // Apply only necessary padding here
                }
            }
        )
    }
}

@Composable
fun FloatingActionButtons() {
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
        contentPadding = PaddingValues(all = 8.dp), // Reduced padding for tighter layout
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

@Composable
fun TopBooksHeader(topBooks: List<Book>) {
    Column {  // Wrap in Column to manage vertical arrangement
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (book in topBooks) {
                TopBookItem(book)
            }
        }
        // Add a divider line below the top books
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}


@Composable
fun TopBookItem(book: Book) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp) // Adjust width according to your layout
            .height(150.dp), // Adjust height according to your layout
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholder_cover),
            contentDescription = "Top Book Cover",
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = book.title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
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
