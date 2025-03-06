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
import androidx.compose.ui.text.style.TextOverflow


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
    val sortedBooks = sampleBooks.sortedWith(
        compareByDescending<Book> { it.userRating }
            .thenByDescending { it.dateAdded }
    )
    val topBooks = sortedBooks.take(3) // Take the top 3 books

    BookwormTheme {
        Scaffold(
            topBar = { BookwormHeader() },
            floatingActionButton = {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    FloatingActionButton(
                        onClick = { navController.navigate("addBook") },
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    FloatingActionButton(
                        onClick = { navController.navigate("editBook")},
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                    {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Book")
                    }
                }
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    TopBooksHeader(topBooks = topBooks, userName = "Ken")
                    Spacer(modifier = Modifier.height(10.dp))
                    BookGrid(
                        books = sortedBooks,
                        modifier = Modifier.padding(paddingValues),
                        onBookClick = { bookId -> navController.navigate("bookDetails/$bookId")})
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
            actions = {
                // Filter Icon Button
                IconButton(onClick = { /* Handle filter icon click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter",
                        modifier = Modifier.size(45.dp).padding(bottom = 10.dp)  // Set the size of the icon
                    )
                }
                // Profile Icon Button
                IconButton(onClick = { /* Handle profile icon click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.userprofile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(45.dp).padding(bottom = 12.dp)  // Set the size of the icon
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

@Composable
fun BookGrid(books: List<Book>, modifier: Modifier = Modifier, onBookClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = modifier
    ) {
        items(books) { book ->
            BookItem(book = book, onClick = { onBookClick(book.id)})
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
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
                painter = painterResource(id = book.imageUrl),
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

@Composable
fun TopBooksHeader(topBooks: List<Book>, userName: String) {
    Column {
        Text(
            text = "$userName's Highest Rated",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.Start)
        )

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

        Divider(
            color = Color.Black,
            thickness = 0.7.dp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun TopBookItem(book: Book) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 107.dp, height = 160.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
        ) {
            Image(
                painter = painterResource(id = book.imageUrl),
                contentDescription = "Top Book Cover",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = book.title,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

// Sample data
val sampleBooks = listOf(
    Book(id = 0, title = "1984", author = "George Orwell", imageUrl = "", userRating = 1.1f, dateAdded = "2024-10-01"),
    Book(id = 1, title = "Brave New World", author = "Aldous Huxley", imageUrl = "", userRating = 0.9f, dateAdded = "2024-10-02"),
    Book(id = 2, title = "To Kill a Mockingbird", author = "Harper Lee", imageUrl = "", userRating = 4.9f, dateAdded = "2024-10-03"),
    Book(id = 3, title = "The Great Gatsby", author = "F. Scott Fitzgerald", imageUrl = "", userRating = 4.7f, dateAdded = "2024-10-04"),
    Book(id = 4, title = "Pride and Prejudice", author = "Jane Austen", imageUrl = "", userRating = 3.0f, dateAdded = "2024-03-05"),
    Book(id = 5, title = "The Hobbit", author = "J.R.R. Tolkien", imageUrl = "", userRating = 2.0f, dateAdded = "2024-10-06"),
    Book(id = 6, title = "Harry Potter and the Sorcerer's Stone", author = "J.K. Rowling", imageUrl = "", userRating = 5.0f, dateAdded = "2024-02-01"),
    Book(id = 7, title = "Harry Potter and the Chamber of Secrets", author = "J.K. Rowling", imageUrl = "", userRating = 5.0f, dateAdded = "2024-10-03"),
    Book(id = 8, title = "Harry Potter and the Prisoner of Azkaban", author = "J.K. Rowling", imageUrl = "", userRating = 5.0f, dateAdded = "2024-09-01"),
    Book(id = 9, title = "Harry Potter and the Goblet of Fire", author = "J.K. Rowling", imageUrl = "", userRating = 4.8f, dateAdded = "2024-10-01"),
    Book(id = 10, title = "Harry Potter and the Order of the Phoenix", author = "J.K. Rowling", imageUrl = "", userRating = 4.8f, dateAdded = "2024-10-01"),
    Book(id = 11, title = "Harry Potter and the Half-Blood Prince", author = "J.K. Rowling", imageUrl = "", userRating = 4.9f, dateAdded = "2024-10-11"),
    Book(id = 12, title = "Harry Potter and the Deathly Hallows", author = "J.K. Rowling", imageUrl = "", userRating = 4.0f, dateAdded = "2024-08-01"),
    Book(id = 13, title = "Adventures of Huckleberry Finn", author = "Mark Twain", imageUrl = "", userRating = 4.5f, dateAdded = "2024-05-01"),
    Book(id = 14, title = "The Fault in Our Stars", author = "John Green", imageUrl = "", userRating = 2.7f, dateAdded = "2024-04-09"),
    Book(id = 15, title = "Anna Karenina", author = "Leo Tolstoy", imageUrl = "", userRating = 1.0f, dateAdded = "2024-10-14")
)

// Book data class
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val imageUrl: Int,
    var userRating: Float,
    var dateAdded: String
)
