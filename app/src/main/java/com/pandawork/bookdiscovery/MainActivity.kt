package com.pandawork.bookdiscovery

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import com.pandawork.bookdiscovery.data.BusinessBooks
import com.pandawork.bookdiscovery.data.HealthAndSafetyBooks
import com.pandawork.bookdiscovery.data.MangaAnimeBooks
import com.pandawork.bookdiscovery.data.PsychologyBooks
import com.pandawork.bookdiscovery.model.Book
import com.pandawork.bookdiscovery.ui.theme.BookDiscoveryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookDiscoveryTheme {
                BookApp()
            }
        }
    }
}

@Composable
fun BookApp() {
    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = {
            BottomAppBar()
        }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            item {
                BookItems()
            }
        }
    }
}

@Composable
private fun BottomAppBar() {
    var isHomeClicked by remember { mutableStateOf(true) }
    var isBookMarkClicked by remember { mutableStateOf(false) }
    var isShoppingBagClicked by remember { mutableStateOf(false) }
    val filledHomeIcon = painterResource(R.drawable.baseline_home_24)
    val filledBookMarkIcon = painterResource(R.drawable.baseline_bookmark_24)
    val filledShoppingBagIcon = painterResource(R.drawable.baseline_shopping_bag_24)
    val outlinedHomeIcon = painterResource(R.drawable.outline_home_24)
    val outlinedBookMarkIcon = painterResource(R.drawable.baseline_bookmark_border_24)
    val outlinedShoppingBagIcon = painterResource(R.drawable.outline_shopping_bag_24)
    BottomAppBar(
        actions = {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                isHomeClicked = true
                isShoppingBagClicked = false
                isBookMarkClicked = false

            }) {
                Icon(
                    painter = if (isHomeClicked) filledHomeIcon else outlinedHomeIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.padding(horizontal = 24.dp))
            IconButton(onClick = {
                isBookMarkClicked = true
                isHomeClicked = false
                isShoppingBagClicked = false
            }) {
                Icon(
                    painter = if (isBookMarkClicked) filledBookMarkIcon else outlinedBookMarkIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.padding(horizontal = 24.dp))
            IconButton(onClick = {
                isShoppingBagClicked = true
                isHomeClicked = false
                isBookMarkClicked = false
            }) {
                Icon(
                    painter = if (isShoppingBagClicked) filledShoppingBagIcon else outlinedShoppingBagIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    )
}

@Composable
fun BookItems() {
    val context = LocalContext.current
    Text(
        "Psychology",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(start = 24.dp, top = 24.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow {
        items(PsychologyBooks.booksList) { book ->
            BookCardWithBook(book, onBookClicked = {
                val url = it.link // Fixed YouTube URL
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            })
        }
    }
    Text(
        "Manga Anime",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(start = 24.dp, top = 24.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow {
        items(MangaAnimeBooks.booksList) { book ->
            BookCardWithBook(book, onBookClicked = {
                val url = it.link // Fixed YouTube URL
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            })
        }
    }
    Text(
        "Business",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(start = 24.dp, top = 24.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow {
        items(BusinessBooks.booksList) { book ->
            BookCardWithBook(book, onBookClicked = {
                val url = it.link // Fixed YouTube URL
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            })
        }
    }

    Text(
        "Health & Safety",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(start = 24.dp, top = 24.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow {
        items(HealthAndSafetyBooks.booksList) { book ->
            BookCardWithBook(book, onBookClicked = {
                val url = it.link // Fixed YouTube URL
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    CenterAlignedTopAppBar(navigationIcon = {
        Icon(
            modifier = Modifier.padding(start = 24.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
        Spacer(Modifier.padding(horizontal = 160.dp))
    }, title = {
        IconButton(onClick = {}) {
            Image(painterResource(R.drawable.profile_pic), contentDescription = null)
        }
    })
}

@Composable
fun BookCardWithBook(book: Book, onBookClicked: (Book) -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onBookClicked(book)
            }
            .padding(14.dp)
            .height(300.dp)
    ) {
        // Book Image - positioned first since it affects text placement
        Image(
            painter = painterResource(id = book.imageRes),
            contentDescription = "Book cover",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .width(160.dp)
                .height(210.dp)
                .align(Alignment.TopCenter)
                .offset(y = 20.dp)
                .zIndex(2f)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 8.dp
                    )
                )
        )

        // Card - positioned below the image
        Card(
            modifier = Modifier
                .width(180.dp)
                .height(210.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = true
                ),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            // Empty card - text will be positioned outside
        }

        // Text content - positioned at fixed point relative to image
        Column(
            modifier = Modifier
                .width(160.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp) // Fixed position below image
        ) {
            Text(
                text = book.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewApp() {
    BookDiscoveryTheme {
        BookApp()
    }
}

@Preview
@Composable
private fun BookPreview() {
    BookDiscoveryTheme {
        BookCardWithBook(PsychologyBooks.booksList[0], onBookClicked = {})
    }
}
