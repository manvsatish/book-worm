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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.style.TextOverflow
import com.zybooks.bookworm.navigation.NavGraph


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


private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    error = Color.Red,
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    error = Color.Red,
    onError = Color.White
)

@Composable
fun BookwormApp(navController: NavHostController) {
    val sortedBooks = sampleBooks.sortedWith(
        compareByDescending<Book> { it.userRating }
            .thenByDescending { it.dateAdded }
    )
    val topBooks = sortedBooks.take(3)

    BookwormTheme {
        Scaffold(
            topBar = { BookwormHeader() },
            floatingActionButton = { FloatingActionButtons(navController) },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues).padding(top = 0.dp)) {
                    TopBooksHeader(topBooks = topBooks, userName = "Ken", onBookClick = { id ->
                        navController.navigate("bookDetails/$id")
                    })
                    BookGrid(
                        books = sortedBooks,
                        onBookClick = { bookId -> navController.navigate("bookDetails/$bookId") }
                    )
                }
            }
        )
    }
}

@Composable
fun FloatingActionButtons(navController: NavHostController) {
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
                // Profile Icon Button
                IconButton(onClick = { /* Handle profile icon click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.userprofile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(45.dp).padding(bottom = 12.dp, end = 4.dp)  // Set the size of the icon
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
    Spacer(Modifier.height(16.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(all = 4.dp),
        modifier = modifier
    ) {
        items(books) { book ->
            BookItem(book = book, onClick = { onBookClick(book.id) })
        }
    }
}

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
fun TopBooksHeader(topBooks: List<Book>, userName: String, onBookClick: (Int) -> Unit) {
    Spacer(Modifier.height(8.dp))
    Column {
        Text(
            text = "$userName's Highest Rated",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .align(Alignment.Start)
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            topBooks.forEach { book ->
                TopBookItem(book, onClick = { onBookClick(book.id) })
            }
        }

        Divider(
            color = Color.Black,
            thickness = 0.7.dp,
            modifier = Modifier.padding(top = 4.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
        )
    }
}


@Composable
fun TopBookItem(book: Book, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 107.dp, height = 160.dp)
            .clickable(onClick = onClick),
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
val sampleBooks = mutableListOf(
    Book(id = 0, title = "1984", author = "George Orwell", imageUrl = R.drawable.nineteen, userRating = 1.1f, dateAdded = "2024-10-01", review = "A classic dystopian novel.", totalPages = 328, pagesRead = 150),
    Book(id = 1, title = "Brave New World", author = "Aldous Huxley", imageUrl = R.drawable.brave, userRating = 0.9f, dateAdded = "2024-10-02", review = "A thought-provoking look at the future.", totalPages = 311, pagesRead = 50),
    Book(id = 2, title = "To Kill a Mockingbird", author = "Harper Lee", imageUrl = R.drawable.mockingbird, userRating = 4.9f, dateAdded = "2024-10-03", review = "A powerful story of race and justice.", totalPages = 281, pagesRead = 281),
    Book(id = 3, title = "The Great Gatsby", author = "F. Scott Fitzgerald", imageUrl = R.drawable.gatsby, userRating = 4.7f, dateAdded = "2024-10-04", review = "A tale of wealth, love, and tragedy.", totalPages = 180, pagesRead = 180),
    Book(id = 4, title = "Pride and Prejudice", author = "Jane Austen", imageUrl = R.drawable.pride, userRating = 3.0f, dateAdded = "2024-03-05", review = "A classic romance with sharp social commentary.", totalPages = 279, pagesRead = 120),
    Book(id = 5, title = "The Hobbit", author = "J.R.R. Tolkien", imageUrl = R.drawable.hobbit, userRating = 2.0f, dateAdded = "2024-10-06", review = "An adventurous journey through Middle Earth.", totalPages = 310, pagesRead = 45),
    Book(id = 6, title = "Harry Potter and the Sorcerer's Stone", author = "J.K. Rowling", imageUrl = R.drawable.sorcerer, userRating = 5.0f, dateAdded = "2024-02-01", review = "The magical beginning of a beloved series.", totalPages = 309, pagesRead = 309),
    Book(id = 7, title = "Harry Potter and the Chamber of Secrets", author = "J.K. Rowling", imageUrl = R.drawable.chamber, userRating = 5.0f, dateAdded = "2024-10-03", review = "The second year at Hogwarts brings more mystery.", totalPages = 341, pagesRead = 150),
    Book(id = 8, title = "Harry Potter and the Prisoner of Azkaban", author = "J.K. Rowling", imageUrl = R.drawable.prisoner, userRating = 5.0f, dateAdded = "2024-09-01", review = "A darker and more thrilling installment.", totalPages = 435, pagesRead = 250),
    Book(id = 9, title = "Harry Potter and the Goblet of Fire", author = "J.K. Rowling", imageUrl = R.drawable.goblet, userRating = 4.8f, dateAdded = "2024-10-01", review = "A magical tournament full of twists and turns.", totalPages = 734, pagesRead = 500),
    Book(id = 10, title = "Harry Potter and the Order of the Phoenix", author = "J.K. Rowling", imageUrl = R.drawable.order, userRating = 4.8f, dateAdded = "2024-10-01", review = "The fight against dark forces intensifies.", totalPages = 766, pagesRead = 300),
    Book(id = 11, title = "Harry Potter and the Half-Blood Prince", author = "J.K. Rowling", imageUrl = R.drawable.phoenix, userRating = 4.9f, dateAdded = "2024-10-11", review = "A deep dive into Voldemort's past and Harry's future.", totalPages = 652, pagesRead = 650),
    Book(id = 12, title = "Harry Potter and the Deathly Hallows", author = "J.K. Rowling", imageUrl = R.drawable.deathly, userRating = 4.0f, dateAdded = "2024-08-01", review = "A gripping conclusion to the series.", totalPages = 759, pagesRead = 200),
    Book(id = 13, title = "Adventures of Huckleberry Finn", author = "Mark Twain", imageUrl = R.drawable.huckleberry, userRating = 4.5f, dateAdded = "2024-05-01", review = "An exploration of freedom and morality.", totalPages = 366, pagesRead = 180),
    Book(id = 14, title = "The Fault in Our Stars", author = "John Green", imageUrl = R.drawable.fault, userRating = 2.7f, dateAdded = "2024-04-09", review = "A heartbreaking yet beautiful love story.", totalPages = 313, pagesRead = 100),
    Book(id = 15, title = "Anna Karenina", author = "Leo Tolstoy", imageUrl = R.drawable.anna, userRating = 1.0f, dateAdded = "2024-10-14", review = "A long, complex narrative about love and betrayal.", totalPages = 864, pagesRead = 250)
)


// Book data class
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val imageUrl: Int,
    var userRating: Float,
    var dateAdded: String,
    var review: String,
    var totalPages: Int,
    var pagesRead: Int
)
