package com.zybooks.bookworm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class) // Opt-in to experimental Material3 API
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}

@Composable
fun BookwormApp(navController: NavHostController) {
    BookwormTheme {
        Scaffold(
            topBar = { BookwormHeader() },
            content = { paddingValues ->
                BookGrid(
                    books = sampleBooks,
                    modifier = Modifier.padding(paddingValues),
                    onBookClick = { bookId -> navController.navigate("bookDetails/$bookId")})
            },
            floatingActionButton = {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    FloatingActionButton(onClick = { navController.navigate("addBook") }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    FloatingActionButton(onClick = { navController.navigate("editBook") }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Book")
                    }
                }
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
fun BookGrid(books: List<Book>, modifier: Modifier = Modifier, onBookClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(books) { book ->
            BookItem(book = book, onClick = { onBookClick(book.id)})
        }
    }
}

//creating the individual book object given lambda in bookgrid
@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 107.dp, height = 160.dp)
            .clickable(onClick = onClick)
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
fun PreviewBookwormApp() {
    BookwormTheme {
        val navController = rememberNavController()
        BookwormApp(navController)
    }
}

// Sample data
val sampleBooks = listOf(
    Book(id = 0, title = "1984", author = "George Orwell", imageUrl = ""),
    Book(id = 1, title = "Brave New World", author = "Aldous Huxley", imageUrl = ""),
    Book(id = 2, title = "To Kill a Mockingbird", author = "Harper Lee", imageUrl = ""),
    Book(id = 3, title = "The Great Gatsby", author = "F. Scott Fitzgerald", imageUrl = ""),
    Book(id = 4, title = "Pride and Prejudice", author = "Jane Austen", imageUrl = ""),
    Book(id = 5, title = "The Hobbit", author = "J.R.R. Tolkien", imageUrl = ""),
    Book(id = 6, title = "Harry Potter and the Sorcerer's Stone", author = "J.K. Rowling", imageUrl = ""),
    Book(id = 7, title = "Harry Potter and the Chamber of Secrets", author = "J.K. Rowling", imageUrl = ""),
    Book(id = 8, title = "Harry Potter and the Prisoner of Azkaban", author = "J.K. Rowling", imageUrl = ""),
    Book(id = 9, title = "Harry Potter and the Goblet of Fire", author = "J.K. Rowling", imageUrl = ""),
    Book(id = 10, title = "Harry Potter and the Order of the Phoenix", author = "J.K. Rowling", imageUrl = ""),
    Book(id = 11, title = "Harry Potter and the Half-Blood Prince", author = "J.K. Rowling", imageUrl = ""),
    Book(id = 12, title = "Harry Potter and the Deathly Hallows", author = "J.K. Rowling", imageUrl = ""),
    Book(id = 13, title = "Adventures of Huckleberry Finn", author = "Mark Twain", imageUrl = ""),
    Book(id = 14, title = "The Fault in Our Stars", author = "John Green", imageUrl = ""),
    Book(id = 15, title = "Anna Karenina", author = "Leo Tolstoy", imageUrl = "")
)

// Book data class
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val imageUrl: String
)
