package com.pandawork.bookdiscovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.pandawork.bookdiscovery.data.Books
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
    Scaffold(topBar = { AppTopBar() }) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.library_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 24.dp, top = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyRow {
                items(Books.booksList) { book ->
                    BookCardWithBook(book)
                }
            }
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
    }, title = {
        Text(
            stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium
        )
    })
}

@Composable
fun BookCardWithBook(book: Book) {
    Box(
        modifier = Modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Card
        Card(
            modifier = Modifier
                .size(width = 150.dp, height = 200.dp)
                .shadow(4.dp), // Add a shadow to the card
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary // Set card color to white
            )
        ) {
            Column(modifier = Modifier.fillMaxHeight().padding(8.dp), verticalArrangement = Arrangement.Bottom) {
                Text(
                    book.name,
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    book.author,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Book (Image)
        Image(
            painter = painterResource(id = book.imageRes),
            contentDescription = "Book cover",
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 16.dp
                    )
                )
                .size(130.dp, 260.dp) // Slightly smaller than the card for visual effect
                .offset(y = (-50).dp) // Offset to move it partially above the card
                .zIndex(1f) // Ensure the image is on top
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 16.dp
                    )
                )
        )
    }
}


@Preview
@Composable
private fun PreviewApp() {
    BookDiscoveryTheme(darkTheme = true) {
        BookApp()
    }
}

@Preview
@Composable
private fun BookPreview() {
    BookDiscoveryTheme(darkTheme = true) {
        BookCardWithBook(Books.booksList[0])
    }
}
