package com.zybooks.bookworm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.zybooks.bookworm.navigation.NavGraph
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import com.zybooks.bookworm.Book
import com.zybooks.bookworm.storage.BookStorageManager
import com.zybooks.bookworm.ui.viewmodel.ThemeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(factory = ThemeViewModel.Factory)
            BookwormTheme(themeViewModel = themeViewModel) {
                // Load books from file and create a mutable state list.
                val books = remember {
                    mutableStateListOf<Book>().apply {
                        addAll(BookStorageManager.loadBooks(this@MainActivity))
                    }
                }
                val navController = rememberNavController()
                // Pass the same state list to the NavGraph.
                NavGraph(navController = navController, books = books, themeViewModel)
            }
        }
    }
}

@Composable
fun BookwormApp(books: MutableList<Book>, navController: NavHostController, themeViewModel: ThemeViewModel) {
    val sortedBooks = books.sortedWith(
        compareByDescending<Book> { it.userRating }
            .thenByDescending { it.dateAdded }
    )
    val topBooks = sortedBooks.take(3)

    BookwormTheme(themeViewModel = themeViewModel) {
        Scaffold(
            topBar = { BookwormHeader(navController) },
            floatingActionButton = { FloatingActionButtons(navController) },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues).padding(top = 0.dp)) {
                    TopBooksHeader(topBooks = topBooks, userName = "Ken", onBookClick = { id ->
                        Log.d("BookwormApp", "Navigating to details of book ID: $id")
                        navController.navigate("bookDetailsScreen1/$id")
                    })
                    BookGrid(
                        books = books,
                        onBookClick = { bookId ->
                            Log.d("BookGrid", "Book item clicked with ID: $bookId")
                            navController.navigate("bookDetailsScreen1/$bookId") }
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
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .border(0.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(15))
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookwormHeader(navController: NavHostController) {
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
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = "Settings",
                            modifier = Modifier.size(45.dp).padding(end = 16.dp)  // Set the size of the icon
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .height(64.dp) // Adjust the height if needed
                .padding(bottom = 8.dp) // Adds padding at the bottom to push the AppBar content up
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(380.dp)
                .height(1.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
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
            .clickable { onClick() }
            .width(120.dp)  // Increased width
            .height(180.dp), // Increased height to accommodate two lines of text better
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = book.imageUrl,
                contentDescription = "Book Cover",
                placeholder = painterResource(id = R.drawable.placeholder_cover), // Local drawable for placeholder
                error = painterResource(id = R.drawable.placeholder_cover),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = book.title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp // Adjusted font size
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewBookwormApp() {
//    BookwormTheme {
//        val navController = rememberNavController()
//        BookwormApp(navController)
//    }
//}

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

        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
            thickness = 0.7.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun TopBookItem(book: Book, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 107.dp, height = 160.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = book.imageUrl,
                contentDescription = "Book Cover",
                placeholder = painterResource(id = R.drawable.placeholder_cover), // Local drawable for placeholder
                error = painterResource(id = R.drawable.placeholder_cover),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
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
    Book(id = 0, title = "1984", author = "George Orwell", imageUrl = "https://m.media-amazon.com/images/I/41sjzA8EGhL._SY445_SX342_.jpg", userRating = 1.1f, dateAdded = "2024-10-01", totalPages = 328, pagesRead = 150, genre = "Classics, Fiction, Science Fiction, Dystopia, Literature, Politics", description = "A masterpiece of rebellion and imprisonment where war is peace freedom is slavery and Big Brother is watching. Thought Police, Big Brother, Orwellian - these words have entered our vocabulary because of George Orwell's classic dystopian novel 1984. The story of one man's Nightmare Odyssey as he pursues a forbidden love affair through a world ruled by warring states and a power structure that controls not only information but also individual thought and memory 1984 is a prophetic haunting tale More relevant than ever before 1984 exposes the worst crimes imaginable the destruction of truth freedom and individuality. With a foreword by Thomas Pynchon. This beautiful paperback edition features deckled edges and french flaps a perfect gift for any occasion", authorBio = "Eric Arthur Blair was an English novelist, poet, essayist, journalist and critic who wrote under the pen name of George Orwell. His work is characterised by lucid prose, social criticism, opposition to all totalitarianism (both authoritarian communism and fascism), and support of democratic socialism.\n" +
            "Orwell is best known for his allegorical novella Animal Farm (1945) and the dystopian novel Nineteen Eighty-Four (1949), although his works also encompass literary criticism, poetry, fiction and polemical journalism. His non-fiction works, including The Road to Wigan Pier (1937), documenting his experience of working-class life in the industrial north of England, and Homage to Catalonia (1938), an account of his experiences soldiering for the Republican faction of the Spanish Civil War (1936–1939), are as critically respected as his essays on politics, literature, language and culture.\n" +
            "Orwell's work remains influential in popular culture and in political culture, and the adjective \"Orwellian\"—describing totalitarian and authoritarian social practices—is part of the English language, like many of his neologisms, such as \"Big Brother\", \"Thought Police\", \"Room 101\", \"Newspeak\", \"memory hole\", \"doublethink\", and \"thoughtcrime\". In 2008, The Times named Orwell the second-greatest British writer since 1945.", userReview = "I always heard this book was a classic, and honestly, it’s both intriguing and terrifying. The way Orwell paints a picture of a totalitarian regime makes me appreciate the freedom I often take for granted. Definitely a heavy read, but so worth it."
    ),
    Book(id = 1, title = "Brave New World", author = "Aldous Huxley", imageUrl = "https://m.media-amazon.com/images/I/416FntWyJQL._SY445_SX342_.jpg", userRating = 0.9f, dateAdded = "2024-10-02", totalPages = 311, pagesRead = 50, genre = "Literature, Science Fiction, Novels, School, Audiobook", description = "Aldous Huxley's profoundly important classic of world literature, Brave New World is a searching vision of an unequal, technologically-advanced future where humans are genetically bred, socially indoctrinated, and pharmaceutically anesthetized to passively uphold an authoritarian ruling order–all at the cost of our freedom, full humanity, and perhaps also our souls. “A genius [who] who spent his life decrying the onward march of the Machine” (The New Yorker), Huxley was a man of incomparable talents: equally an artist, a spiritual seeker, and one of history’s keenest observers of human nature and civilization. Brave New World, his masterpiece, has enthralled and terrified millions of readers, and retains its urgent relevance to this day as both a warning to be heeded as we head into tomorrow and as thought-provoking, satisfying work of literature. Written in the shadow of the rise of fascism during the 1930s, Brave New Worldd likewise speaks to a 21st-century world dominated by mass-entertainment, technology, medicine and pharmaceuticals, the arts of persuasion, and the hidden influence of elites.", authorBio = "Aldous Leonard Huxley was an English writer and philosopher. His bibliography spans nearly 50 books, including non-fiction works, as well as essays, narratives, and poems.\n" +
            "Born into the prominent Huxley family, he graduated from Balliol College, Oxford, with a degree in English literature. Early in his career, he published short stories and poetry and edited the literary magazine Oxford Poetry, before going on to publish travel writing, satire, and screenplays. He spent the latter part of his life in the United States, living in Los Angeles from 1937 until his death. By the end of his life, Huxley was widely acknowledged as one of the foremost intellectuals of his time. He was nominated for the Nobel Prize in Literature nine times, and was elected Companion of Literature by the Royal Society of Literature in 1962.\n" +
            "Huxley was a pacifist. He grew interested in philosophical mysticism, as well as universalism, addressing these subjects in his works such as The Perennial Philosophy (1945), which illustrates commonalities between Western and Eastern mysticism, and The Doors of Perception (1954), which interprets his own psychedelic experience with mescaline. In his most famous novel Brave New World (1932) and his final novel Island (1962), he presented his visions of dystopia and utopia, respectively.", userReview = "This book made me think a lot about where society is heading with our dependence on technology and comfort. It's chilling to see parallels in our modern world. A must-read for anyone into speculative sci-fi."
    ),
    Book(id = 2, title = "To Kill a Mockingbird", author = "Harper Lee", imageUrl = "https://m.media-amazon.com/images/I/51IXWZzlgSL._SY445_SX342_.jpg", userRating = 4.9f, dateAdded = "2024-10-03", totalPages = 281, pagesRead = 281, genre = "Fiction, Historical Fiction, School, Literature, Young Adult", description = "One of the best-loved stories of all time, To Kill a Mockingbird has been translated into more than forty languages, sold more than forty million copies worldwide, served as the basis for an enormously popular motion picture, and was voted one of the best novels of the twentieth century by librarians across the country. A gripping, heart-wrenching, and wholly remarkable coming-of-age tale in a South poisoned by virulent prejudice, it views a world of great beauty and savage iniquities through the eyes of a young girl, as her father — a crusading local lawyer — risks everything to defend a black man unjustly accused of a terrible crime.", authorBio = "Nelle Harper Lee was an American novelist whose 1960 novel To Kill a Mockingbird won the 1961 Pulitzer Prize and became a classic of modern American literature. She assisted her close friend Truman Capote in his research for the book In Cold Blood (1966). Her second and final novel, Go Set a Watchman, was an earlier draft of Mockingbird, set at a later date, that was published in July 2015 as a sequel.\n" +
            "The plot and characters of To Kill a Mockingbird are loosely based on Lee's observations of her family and neighbors in Monroeville, Alabama, as well as a childhood event that occurred near her hometown in 1936. The novel deals with racist attitudes, the irrationality of adult attitudes towards race and class in the Deep South of the 1930s, as depicted through the eyes of two children.\n" +
            "Lee received numerous accolades and honorary degrees, including the Presidential Medal of Freedom in 2007, which was awarded for her contribution to literature.", userReview = "I was moved by the profound themes of racial injustice and moral growth. Atticus Finch has become one of my favorite characters in literature, embodying integrity and righteousness."
    ),
    Book(id = 3, title = "The Great Gatsby", author = "F. Scott Fitzgerald", imageUrl = "https://m.media-amazon.com/images/I/41k428xb0aL._SY445_SX342_.jpg", userRating = 4.7f, dateAdded = "2024-10-04", totalPages = 180, pagesRead = 180, genre = "Fiction, School, Historical Fiction, Romance, Literature, Novels", description = "The Great Gatsby, F. Scott Fitzgerald’s third book, stands as the supreme achievement of his career. First published in 1925, this quintessential novel of the Jazz Age has been acclaimed by generations of readers. The story of the mysteriously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan, of lavish parties on Long Island at a time when The New York Times noted “gin was the national drink and sex the national obsession,” it is an exquisitely crafted tale of America in the 1920s.", authorBio = "Francis Scott Key Fitzgerald, widely known simply as Scott Fitzgerald, was an American novelist, essayist, and short story writer. He is best known for his novels depicting the flamboyance and excess of the Jazz Age, a term he popularized in his short story collection Tales of the Jazz Age. During his lifetime, he published four novels, four story collections, and 164 short stories. Although he achieved temporary popular success and fortune in the 1920s, Fitzgerald received critical acclaim only after his death and is now widely regarded as one of the greatest American writers of the 20th century.\n" +
            "Born into a middle-class family in Saint Paul, Minnesota, Fitzgerald was raised primarily in New York state. He attended Princeton University where he befriended future literary critic Edmund Wilson. Owing to a failed romantic relationship with Chicago socialite Ginevra King, he dropped out in 1917 to join the United States Army during World War I. While stationed in Alabama, he met Zelda Sayre, a Southern debutante who belonged to Montgomery's exclusive country-club set. Although she initially rejected Fitzgerald's marriage proposal due to his lack of financial prospects, Zelda agreed to marry him after he published the commercially successful This Side of Paradise (1920). The novel became a cultural sensation and cemented his reputation as one of the eminent writers of the decade.\n" +
            "His second novel, The Beautiful and Damned (1922), propelled him further into the cultural elite. To maintain his affluent lifestyle, he wrote numerous stories for popular magazines such as The Saturday Evening Post, Collier's Weekly, and Esquire. During this period, Fitzgerald frequented Europe, where he befriended modernist writers and artists of the \"Lost Generation\" expatriate community, including Ernest Hemingway. His third novel, The Great Gatsby (1925), received generally favorable reviews but was a commercial failure, selling fewer than 23,000 copies in its first year. Despite its lackluster debut, The Great Gatsby is now hailed by some literary critics as the \"Great American Novel\". Following the deterioration of his wife's mental health and her placement in a mental institute for schizophrenia, Fitzgerald completed his final novel, Tender Is the Night (1934).\n" +
            "Struggling financially because of the declining popularity of his works during the Great Depression, Fitzgerald moved to Hollywood, where he embarked upon an unsuccessful career as a screenwriter. While living in Hollywood, he cohabited with columnist Sheilah Graham, his final companion before his death. After a long struggle with alcoholism, he attained sobriety only to die of a heart attack in 1940, at 44. His friend Edmund Wilson edited and published an unfinished fifth novel, The Last Tycoon (1941), after Fitzgerald's death. In 1993, a new edition was published as The Love of the Last Tycoon, edited by Matthew J. Bruccoli.", userReview = "I got totally caught up in the glamour and sadness of Gatsby's world. Fitzgerald’s critique of the American Dream is portrayed with such beautiful, poignant prose."
    ),
    Book(id = 4, title = "Pride and Prejudice", author = "Jane Austen", imageUrl = "https://m.media-amazon.com/images/I/51G7Ie1hExL._SY445_SX342_.jpg", userRating = 3.0f, dateAdded = "2024-03-05", totalPages = 279, pagesRead = 120, genre = "Fiction, Historical Fiction, Historical, Literature, Audiobook", description = "Pride and Prejudice has charmed generations of readers for more than two centuries. Jane Austen's much-adapted novel is famed for its witty, spirited heroine, sensational romances, and deft remarks on the triumphs and pitfalls of social convention. Author Jane Austen (1775-1817) was an English novelist whose works of social realism achieved unprecedented critical and popular success, though Austen herself remained an anonymous writer throughout her life.", authorBio = "Jane Austen was an English novelist known primarily for her six novels, which implicitly interpret, critique, and comment upon the British landed gentry at the end of the 18th century. Austen's plots often explore the dependence of women on marriage for the pursuit of favourable social standing and economic security. Her works are an implicit critique of the novels of sensibility of the second half of the 18th century and are part of the transition to 19th-century literary realism. Her deft use of social commentary, realism and biting irony have earned her acclaim among critics and scholars.\n" +
            "The anonymously published Sense and Sensibility (1811), Pride and Prejudice (1813), Mansfield Park (1814), and Emma (1816), were a modest success but brought her little fame in her lifetime. She wrote two other novels—Northanger Abbey and Persuasion, both published posthumously in 1817—and began another, eventually titled Sanditon, but died before its completion. She also left behind three volumes of juvenile writings in manuscript, the short epistolary novel Lady Susan, and the unfinished novel The Watsons.\n" +
            "Since her death Austen's novels have rarely been out of print. A significant transition in her reputation occurred in 1833, when they were republished in Richard Bentley's Standard Novels series (illustrated by Ferdinand Pickering and sold as a set). They gradually gained wide acclaim and popular readership. In 1869, fifty-two years after her death, her nephew's publication of A Memoir of Jane Austen introduced a compelling version of her writing career and supposedly uneventful life to an eager audience. Her work has inspired a large number of critical essays and has been included in many literary anthologies. Her novels have also inspired many films, including 1940's Pride and Prejudice, 1995's Sense and Sensibility and 2016's Love & Friendship.", userReview = "Elizabeth Bennet is the wittiest literary heroine! This book’s humor and insights into different characters' behaviors and societal norms are timeless."
    ),
    Book(id = 5, title = "The Hobbit", author = "J.R.R. Tolkien", imageUrl = "https://m.media-amazon.com/images/I/712cDO7d73L._SY522_.jpg", userRating = 2.0f, dateAdded = "2024-10-06", totalPages = 310, pagesRead = 45, genre = "Fantasy\n" +
            "Graphic Novels, Classics, Fiction, Adventure", description = "First published over 50 years ago, J.R.R. Tolkien's 'The Hobbit' has become one of the best-loved books of all time. Now Tolkien's fantasy classic has been adapted into a fully painted graphic novel.'The Hobbit' is the story of Bilbo Baggins…a quiet and contented hobbit whose life is turned upside down when he joins the wizard Gandalf and thirteen dwarves on their quest to reclaim the dwarves' stolen treasure. It is a journey fraught with danger – and in the end it is Bilbo alone who must face the guardian of this treasure, the most-dreaded dragon Smaug. \nIllustrated in full colour throughout, and accompanied by the carefully abridged text of the original novel, this handsome authorised edition will introduce new generations to a magical masterpiece – and be treasured by Hobbit fans of all ages, everywhere.", authorBio = "Charles \"Chuck\" Dixon is an American comic book writer, perhaps best-known for long runs on Batman titles in the 1990s. His earliest comics work was writing Evangeline first for Comico Comics in 1984 (then later for First Comics, who published the on-going series), on which he worked with his then-wife, the artist Judith Hunt. His big break came one year later, when editor Larry Hama hired him to write back-up stories for Marvel Comics' The Savage Sword of Conan.\nIn 1986, he began working for Eclipse Comics, writing Airboy with artist Tim Truman. Continuing to write for both Marvel and (mainly) Eclipse on these titles, as well as launching Strike! with artist Tom Lyle in August 1987 and Valkyrie with artist Paul Gulacy in October 1987, he began work on Carl Potts' Alien Legion series for Marvel's Epic Comics imprint, under editor Archie Goodwin. He also produced a three-issue adaptation of J. R. R. Tolkien's The Hobbit for Eclipse with artist David Wenzel between 1989 and 1990, and began writing Marc Spector: Moon Knight in June 1989.\nHis Punisher OGN Kingdom Gone (August, 1990) led to him working on the monthly The Punisher War Journal (and later, more monthly and occasional Punisher titles), and also brought him to the attention of DC Comics editor Denny O'Neil, who asked him to produce a Robin mini-series. The mini proved popular enough to spawn two sequels - The Joker's Wild (1991) and Cry of the Huntress (1992) - which led to both an ongoing monthly series (which Dixon wrote for 100 issues before leaving to work with CrossGen Comics), and to Dixon working on Detective Comics from #644-738 through the major Batman stories KnightFall & KnightsEnd (for which he helped create the key character of Bane), DC One Million , Contagion , Legacy , Cataclysm and No Man's Land . Much of his run was illustrated by Graham Nolan.\nHe was DC's most prolific Batman-writer in the mid-1990s (rivalled perhaps in history by Bill Finger and Dennis O'Neil) - in addition to writing Detective Comics he pioneered the individual series for Robin , Nightwing (which he wrote for 70 issues, and returned to briefly with 2005's #101) and Batgirl , as well as creating the team and book Birds of Prey .\nWhile writing multiple Punisher and Batman comics (and October 1994's Punisher/Batman crossover), he also found time to launch Team 7 for Jim Lee's WildStorm/Image and Prophet for Rob Liefeld's Extreme Studios. He also wrote many issues of Catwoman and Green Arrow , regularly having about seven titles out each and every month between the years 1993 and 1998.\nIn March, 2002, Dixon turned his attention to CrossGen's output, salthough he co-wrote with Scott Beatty the origin of Barbara Gordon's Batgirl in 2003's Batgirl: Year One. For CrossGen he took over some of the comics of the out-going Mark Waid, taking over Sigil from #21, and Crux with #13. He launched Way of the Rat in June 2002, Brath (March '03), The Silken Ghost (June '03) and the pirate comic El Cazador (Oct '03), as well as editing Robert Rodi's non-Sigilverse The Crossovers. He also wrote the Ruse spin-off Archard's Agents one-shots in January and November '03 and April '04, the last released shortly before CrossGen's complete collapse forced the cancellation of all of its comics, before which Dixon wrote a single issue of Sojourn (May '04). Dixon's Way of the Rat #24, Brath #14 and El Cazador #6 were among the last comics released from the then-bankrupt publisher.\nOn June 10, 2008, Dixon announced on his forum that he was no longer \"employed by DC Comics in any capacity.\"", userReview = "My journey through Middle Earth was filled with adventure and whimsy. It’s a prelude to the more intense Lord of the Rings but holds its own with charm and warmth."
    ),
    Book(id = 6, title = "Harry Potter and the Sorcerer's Stone", author = "J.K. Rowling", imageUrl = "https://m.media-amazon.com/images/I/5152XTq24+L._SY445_SX342_.jpg", userRating = 5.0f, dateAdded = "2024-02-01", totalPages = 309, pagesRead = 309, genre = "Fantasy, Fiction, Young Adult, Magic, Childrens, Middle Grade", description = "Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harry's eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!\n", authorBio = "Although she writes under the pen name J.K. Rowling, pronounced like rolling, her name when her first Harry Potter book was published was simply Joanne Rowling. Anticipating that the target audience of young boys might not want to read a book written by a woman, her publishers demanded that she use two initials, rather than her full name. As she had no middle name, she chose K as the second initial of her pen name, from her paternal grandmother Kathleen Ada Bulgen Rowling. She calls herself Jo and has said, \"No one ever called me 'Joanne' when I was young, unless they were angry.\" Following her marriage, she has sometimes used the name Joanne Murray when conducting personal business. During the Leveson Inquiry she gave evidence under the name of Joanne Kathleen Rowling. In a 2012 interview, Rowling noted that she no longer cared that people pronounced her name incorrectly.", userReview = "I grew up with Harry and revisiting this story brought back the magic of my childhood. Rowling’s world-building is simply magical."
    ),
    Book(id = 7, title = "Harry Potter and the Chamber of Secrets", author = "J.K. Rowling", imageUrl = "https://m.media-amazon.com/images/I/51n-RPrkgSL._SY445_SX342_.jpg", userRating = 5.0f, dateAdded = "2024-10-03", totalPages = 341, pagesRead = 150, genre = "Fantasy, Fiction, Young Adult, Magic, Childrens, Middle Grade", description = "Ever since Harry Potter had come home for the summer, the Dursleys had been so mean and hideous that all Harry wanted was to get back to the Hogwarts School for Witchcraft and Wizardry. But just as he’s packing his bags, Harry receives a warning from a strange impish creature who says that if Harry returns to Hogwarts, disaster will strike.", authorBio = "Although she writes under the pen name J.K. Rowling, pronounced like rolling, her name when her first Harry Potter book was published was simply Joanne Rowling. Anticipating that the target audience of young boys might not want to read a book written by a woman, her publishers demanded that she use two initials, rather than her full name. As she had no middle name, she chose K as the second initial of her pen name, from her paternal grandmother Kathleen Ada Bulgen Rowling. She calls herself Jo and has said, \"No one ever called me 'Joanne' when I was young, unless they were angry.\" Following her marriage, she has sometimes used the name Joanne Murray when conducting personal business. During the Leveson Inquiry she gave evidence under the name of Joanne Kathleen Rowling. In a 2012 interview, Rowling noted that she no longer cared that people pronounced her name incorrectly.", userReview = "More mysteries and magic in Harry's second year at Hogwarts kept me on the edge of my seat. The development of the characters and the dark undercurrents set the stage for deeper stories."
    ),
    Book(id = 8, title = "Harry Potter and the Prisoner of Azkaban", author = "J.K. Rowling", imageUrl = "https://m.media-amazon.com/images/I/51CLmyCCJnL._SY445_SX342_.jpg", userRating = 5.0f, dateAdded = "2024-09-01", totalPages = 435, pagesRead = 250, genre = "Fantasy, Fiction, Young Adult, Magic, Childrens, Middle Grade", description = "Vol. 3 of J.K. Rowling's Harry Potter series.", authorBio = "Although she writes under the pen name J.K. Rowling, pronounced like rolling, her name when her first Harry Potter book was published was simply Joanne Rowling. Anticipating that the target audience of young boys might not want to read a book written by a woman, her publishers demanded that she use two initials, rather than her full name. As she had no middle name, she chose K as the second initial of her pen name, from her paternal grandmother Kathleen Ada Bulgen Rowling. She calls herself Jo and has said, \"No one ever called me 'Joanne' when I was young, unless they were angry.\" Following her marriage, she has sometimes used the name Joanne Murray when conducting personal business. During the Leveson Inquiry she gave evidence under the name of Joanne Kathleen Rowling. In a 2012 interview, Rowling noted that she no longer cared that people pronounced her name incorrectly.", userReview = "This book has always been my favorite of the series! The introduction of Sirius Black and the dementors added layers to the plot that made it impossible to put down."
    ),
    Book(id = 9, title = "Harry Potter and the Goblet of Fire", author = "J.K. Rowling", imageUrl = "https://m.media-amazon.com/images/I/51vOPzoNd-L._SY445_SX342_.jpg", userRating = 4.8f, dateAdded = "2024-10-01", totalPages = 734, pagesRead = 500, genre = "Fantasy, Fiction, Young Adult, Magic, Childrens, Middle Grade", description = "Lord Voldemort, the dark wizard responsible for the deaths of Harry's parents, is growing stronger. At the Quidditch World Cup, Voldemort's signature Dark Mark appears in the sky over the stadium, causing pandemonium. The lightning-bolt-shaped scar on Harry's forehead is sporadically causing him agonizing pain, and he is also hearing disturbing voices. Harry realizes that all this is the result of a strong connection between himself and the Dark Lord, one that is putting him in grave danger. Back at Hogwarts, the students are getting ready for the upcoming Triwizard Tournament. Witches and wizards from two other schools are coming to Hogwarts for the year to compete in a series of grueling contests. The tournament is open only to students age 17 and above, but when someone secretly enters Harry's name, he is forced to compete. How can a 14-year-old possibly pass tests that might be fatal to an advanced wizard? And with the threat of Lord Voldemort looming, will he be able to focus on the tournament at all? For Harry, his friends, and everyone in the Wizarding world, the stakes are about to become much higher. This fourth installment, with a heart-pounding and emotional climax, serves as a turning point in the series, for the reader and for Harry himself.", authorBio = "Although she writes under the pen name J.K. Rowling, pronounced like rolling, her name when her first Harry Potter book was published was simply Joanne Rowling. Anticipating that the target audience of young boys might not want to read a book written by a woman, her publishers demanded that she use two initials, rather than her full name. As she had no middle name, she chose K as the second initial of her pen name, from her paternal grandmother Kathleen Ada Bulgen Rowling. She calls herself Jo and has said, \"No one ever called me 'Joanne' when I was young, unless they were angry.\" Following her marriage, she has sometimes used the name Joanne Murray when conducting personal business. During the Leveson Inquiry she gave evidence under the name of Joanne Kathleen Rowling. In a 2012 interview, Rowling noted that she no longer cared that people pronounced her name incorrectly.", userReview = "The Triwizard Tournament was thrilling! This book perfectly blends action, emotion, and suspense."
    ),
    Book(id = 10, title = "Harry Potter and the Order of the Phoenix", author = "J.K. Rowling", imageUrl = "https://m.media-amazon.com/images/I/91TzeItvNFL._SY522_.jpg", userRating = 4.8f, dateAdded = "2024-10-01", totalPages = 766, pagesRead = 300, genre = "Fantasy, Fiction, Young Adult, Magic, Childrens, Middle Grade", description = "Harry Potter is about to start his fifth year at Hogwarts School of Witchcraft and Wizardry. Unlike most schoolboys, Harry never enjoys his summer holidays, but this summer is even worse than usual. The Dursleys, of course, are making his life a misery, but even his best friends, Ron and Hermione, seem to be neglecting him.", authorBio = "Although she writes under the pen name J.K. Rowling, pronounced like rolling, her name when her first Harry Potter book was published was simply Joanne Rowling. Anticipating that the target audience of young boys might not want to read a book written by a woman, her publishers demanded that she use two initials, rather than her full name. As she had no middle name, she chose K as the second initial of her pen name, from her paternal grandmother Kathleen Ada Bulgen Rowling. She calls herself Jo and has said, \"No one ever called me 'Joanne' when I was young, unless they were angry.\" Following her marriage, she has sometimes used the name Joanne Murray when conducting personal business. During the Leveson Inquiry she gave evidence under the name of Joanne Kathleen Rowling. In a 2012 interview, Rowling noted that she no longer cared that people pronounced her name incorrectly.", userReview = "I felt Harry’s frustrations and challenges deeply in this one. It’s darker but incredibly engaging as the stakes are higher."
    ),
    Book(id = 11, title = "Harry Potter and the Half-Blood Prince", author = "J.K. Rowling", imageUrl = "https://m.media-amazon.com/images/I/51uO1pQc5oL._SY445_SX342_.jpg", userRating = 4.5f, dateAdded = "2024-10-11", totalPages = 652, pagesRead = 650, genre = "Fantasy, Fiction, Young Adult, Magic, Childrens, Middle Grade", description = "The war against Voldemort is not going well: even Muggle governments are noticing. Ron scans the obituary pages of The Daily Prophet looking for familiar names. Dumbledore is absent from Hogwarts for long stretches of time, and the Order of the Phoenix has already suffered losses. And yet...", authorBio = "Although she writes under the pen name J.K. Rowling, pronounced like rolling, her name when her first Harry Potter book was published was simply Joanne Rowling. Anticipating that the target audience of young boys might not want to read a book written by a woman, her publishers demanded that she use two initials, rather than her full name. As she had no middle name, she chose K as the second initial of her pen name, from her paternal grandmother Kathleen Ada Bulgen Rowling. She calls herself Jo and has said, \"No one ever called me 'Joanne' when I was young, unless they were angry.\" Following her marriage, she has sometimes used the name Joanne Murray when conducting personal business. During the Leveson Inquiry she gave evidence under the name of Joanne Kathleen Rowling. In a 2012 interview, Rowling noted that she no longer cared that people pronounced her name incorrectly.", userReview = "Delving into Voldemort’s past was fascinating. This book sets up the climax of the series beautifully and tragically."
    ),
    Book(id = 12, title = "Harry Potter and the Deathly Hallows", author = "J.K. Rowling", imageUrl = "https://m.media-amazon.com/images/I/51IZtAdNrHL._SY445_SX342_.jpg", userRating = 4.0f, dateAdded = "2024-08-01", totalPages = 759, pagesRead = 200, genre = "Fantasy, Fiction, Young Adult, Magic, Childrens, Middle Grade", description = "It's no longer safe for Harry at Hogwarts, so he and his best friends, Ron and Hermione, are on the run. Professor Dumbledore has given them clues about what they need to do to defeat the dark wizard, Lord Voldemort, once and for all, but it's up to them to figure out what these hints and suggestions really mean. Their cross-country odyssey has them searching desperately for the answers, while evading capture or death at every turn. At the same time, their friendship, fortitude, and sense of right and wrong are tested in ways they never could have imagined. The ultimate battle between good and evil that closes out this final chapter of the epic series takes place where Harry's Wizarding life began: at Hogwarts. The satisfying conclusion offers shocking last-minute twists, incredible acts of courage, powerful new forms of magic, and the resolution of many mysteries. Above all, this intense, cathartic book serves as a clear statement of the message at the heart of the Harry Potter series: that choice matters much more than destiny, and that love will always triumph over death.", authorBio = "Although she writes under the pen name J.K. Rowling, pronounced like rolling, her name when her first Harry Potter book was published was simply Joanne Rowling. Anticipating that the target audience of young boys might not want to read a book written by a woman, her publishers demanded that she use two initials, rather than her full name. As she had no middle name, she chose K as the second initial of her pen name, from her paternal grandmother Kathleen Ada Bulgen Rowling. She calls herself Jo and has said, \"No one ever called me 'Joanne' when I was young, unless they were angry.\" Following her marriage, she has sometimes used the name Joanne Murray when conducting personal business. During the Leveson Inquiry she gave evidence under the name of Joanne Kathleen Rowling. In a 2012 interview, Rowling noted that she no longer cared that people pronounced her name incorrectly.", userReview = "What a conclusion! This book was a rollercoaster of emotions, tying up all the loose ends and leaving me satisfied yet longing for more."
    ),
    Book(id = 13, title = "Adventures of Huckleberry Finn", author = "Mark Twain", imageUrl = "https://m.media-amazon.com/images/I/41sDfHhNnrL._SY445_SX342_.jpg", userRating = 4.5f, dateAdded = "2024-05-01", totalPages = 366, pagesRead = 180, genre = "", description = "A nineteenth-century boy from a Mississippi River town recounts his adventures as he travels down the river with a runaway slave, encountering a family involved in a feud, two scoundrels pretending to be royalty, and Tom Sawyer's aunt who mistakes him for Tom.", authorBio = "Samuel Langhorne Clemens, known by the pen name Mark Twain, was an American writer, humorist and essayist. He was praised as the \"greatest humorist the United States has produced,\" with William Faulkner calling him \"the father of American literature.\" His novels include The Adventures of Tom Sawyer (1876) and its sequel, Adventures of Huckleberry Finn (1884), with the latter often called the \"Great American Novel.\" Twain also wrote A Connecticut Yankee in King Arthur's Court (1889) and Pudd'nhead Wilson (1894), and co-wrote The Gilded Age: A Tale of Today (1873) with Charles Dudley Warner.", userReview = "Huck's adventures along the Mississippi River offered me a snapshot of various human characters and deep themes under the guise of a child's innocent perspective."
    ),
    Book(id = 14, title = "The Fault in Our Stars", author = "John Green", imageUrl = "https://m.media-amazon.com/images/I/41YN7Ng8XYL._SY445_SX342_.jpg", userRating = 2.7f, dateAdded = "2024-04-09", totalPages = 313, pagesRead = 100, genre = "Young Adult, Fiction, Contemporary, Realistic Fiction, Teen", description = "Despite the tumor-shrinking medical miracle that has bought her a few years, Hazel has never been anything but terminal, her final chapter inscribed upon diagnosis. But when a gorgeous plot twist named Augustus Waters suddenly appears at Cancer Kid Support Group, Hazel's story is about to be completely rewritten.", authorBio = "John Green's first novel, Looking for Alaska, won the 2006 Michael L. Printz Award presented by the American Library Association. His second novel, An Abundance of Katherines, was a 2007 Michael L. Printz Award Honor Book and a finalist for the Los Angeles Times Book Prize. His next novel, Paper Towns, is a New York Times bestseller and won the Edgar Allen Poe Award for Best YA Mystery. In January 2012, his most recent novel, The Fault in Our Stars, was met with wide critical acclaim, unprecedented in Green's career. The praise included rave reviews in Time Magazine and The New York Times, on NPR, and from award-winning author Markus Zusak. The book also topped the New York Times Children's Paperback Bestseller list for several weeks. Green has also coauthored a book with David Levithan called Will Grayson, Will Grayson, published in 2010. The film rights for all his books, with the exception of Will Grayson Will Grayson, have been optioned to major Hollywood Studios.", userReview = "Heartbreaking yet beautifully written. Hazel and Augustus' story is a poignant reminder of the fragility of life and the power of love."
    ),
    Book(id = 15, title = "Anna Karenina", author = "Leo Tolstoy", imageUrl = "https://m.media-amazon.com/images/I/31lAayxUeAL._SY445_SX342_.jpg", userRating = 1.0f, dateAdded = "2024-10-14", totalPages = 864, pagesRead = 250, genre = "Fiction, Romance, Russia, Historical Fiction, Russian Literature", description = "Acclaimed by many as the world's greatest novel, Anna Karenina provides a vast panorama of contemporary life in Russia and of humanity in general. In it Tolstoy uses his intense imaginative insight to create some of the most memorable characters in all of literature. Anna is a sophisticated woman who abandons her empty existence as the wife of Karenin and turns to Count Vronsky to fulfil her passionate nature - with tragic consequences. Levin is a reflection of Tolstoy himself, often expressing the author's own views and convictions.", authorBio = "Lev Nikolayevich Tolstoy (Russian: Лев Николаевич Толстой; most appropriately used Liev Tolstoy; commonly Leo Tolstoy in Anglophone countries) was a Russian writer who primarily wrote novels and short stories. Later in life, he also wrote plays and essays. His two most famous works, the novels War and Peace and Anna Karenina, are acknowledged as two of the greatest novels of all time and a pinnacle of realist fiction. Many consider Tolstoy to have been one of the world's greatest novelists. Tolstoy is equally known for his complicated and paradoxical persona and for his extreme moralistic and ascetic views, which he adopted after a moral crisis and spiritual awakening in the 1870s, after which he also became noted as a moral thinker and social reformer.", userReview = "Tolstoy's exploration of love, betrayal, and societal pressures in imperial Russia is both grand and intimate. Anna is a tragic figure that I couldn't help but empathize with, despite her flaws."
    )
)
