package com.zybooks.bookworm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.zybooks.bookworm.ui.theme.BookwormTheme

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
    BookwormTheme {
        Scaffold(
            floatingActionButton = {
                AddBookButton()
            },
            content = { paddingValues ->
                BookList(books = sampleBooks, modifier = Modifier.padding(paddingValues))
            }
        )
    }
}

@Composable
fun BookList(books: List<Book>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(books) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Row(modifier = Modifier.padding(8.dp)) {
        // Replace 'R.drawable.placeholder' with an actual drawable resource ID for the book cover image
        Image(
            painter = painterResource(id = R.drawable.placeholder_cover),
            contentDescription = "Book Cover",
            modifier = Modifier.size(60.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.titleLarge)
            Text(text = "by ${book.author}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun AddBookButton() {
    FloatingActionButton(onClick = { /* TODO: Implement your add book action */ }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book")
    }
}

// Preview of the BookwormApp
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookwormApp()
}

// Sample data
val sampleBooks = listOf(
    Book(title = "1984", author = "George Orwell", imageUrl = ""),
    Book(title = "Brave New World", author = "Aldous Huxley", imageUrl = "")
)

// Book data class
data class Book(
    val title: String,
    val author: String,
    val imageUrl: String
)
