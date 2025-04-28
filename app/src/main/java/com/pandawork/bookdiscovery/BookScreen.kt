package com.pandawork.bookdiscovery

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@Composable
fun BookApp() {
    Scaffold(topBar = { AppTopBar() }, bottomBar = { BottomAppBar() }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
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
    var selectedTab by remember { mutableStateOf(Tab.HOME) }

    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TabIcon(
                    selected = selectedTab == Tab.HOME,
                    filledIcon = painterResource(R.drawable.baseline_home_24),
                    outlinedIcon = painterResource(R.drawable.outline_home_24),
                    onClick = { selectedTab = Tab.HOME })
                Spacer(Modifier.padding(horizontal = 24.dp))
                TabIcon(
                    selected = selectedTab == Tab.BOOKMARK,
                    filledIcon = painterResource(R.drawable.baseline_bookmark_24),
                    outlinedIcon = painterResource(R.drawable.baseline_bookmark_border_24),
                    onClick = { selectedTab = Tab.BOOKMARK })
                Spacer(Modifier.padding(horizontal = 24.dp))
                TabIcon(
                    selected = selectedTab == Tab.SHOP,
                    filledIcon = painterResource(R.drawable.baseline_shopping_bag_24),
                    outlinedIcon = painterResource(R.drawable.outline_shopping_bag_24),
                    onClick = { selectedTab = Tab.SHOP })
                Spacer(modifier = Modifier.weight(1f))
            }
        })

}

@Composable
private fun TabIcon(
    selected: Boolean, filledIcon: Painter, outlinedIcon: Painter, onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = if (selected) filledIcon else outlinedIcon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

private enum class Tab {
    HOME, BOOKMARK, SHOP
}

@Composable
fun BookItems() {
    val context = LocalContext.current
    Text(
        stringResource(R.string.psychology_category_title),
        style = MaterialTheme.typography.headlineLarge,
        modifier =
            Modifier.padding(
                start = dimensionResource(R.dimen.padding_large),
                top = dimensionResource(R.dimen.padding_large)
            )
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
    LazyRow {
        items(PsychologyBooks.booksList) { book ->
            BookCardWithBook(
                Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(300.dp), book, onBookClicked = {
                    val url = it.link
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }
    Text(
        stringResource(R.string.manga_anime_category_title),
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            top = dimensionResource(R.dimen.padding_large)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow {
        items(MangaAnimeBooks.booksList) { book ->
            BookCardWithBook(
                Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(300.dp), book, onBookClicked = {
                    val url = it.link
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }
    Text(
        stringResource(R.string.business_category_title),
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            top = dimensionResource(R.dimen.padding_large)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow {
        items(BusinessBooks.booksList) { book ->
            BookCardWithBook(
                Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(300.dp), book, onBookClicked = {
                    val url = it.link
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }

    Text(
        stringResource(R.string.health_safety_category_title),
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            top = dimensionResource(R.dimen.padding_large)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow {
        items(HealthAndSafetyBooks.booksList) { book ->
            BookCardWithBook(
                Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(300.dp), book, onBookClicked = {
                    val url = it.link
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_large)),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        },
        title = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = {}) {
                    Image(painterResource(R.drawable.profile_pic), contentDescription = null)
                }
                Spacer(Modifier.width(24.dp))
            }
        })
}

@Composable
fun BookCardWithBook(modifier: Modifier = Modifier, book: Book, onBookClicked: (Book) -> Unit) {
    Box(
        modifier = modifier.clickable {
            onBookClicked(book)
        }) {
        // Book Image - positioned first since it affects text placement
        Image(
            painter = painterResource(id = book.imageRes),
            contentDescription = "Book cover",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .width(160.dp)
                .height(210.dp)
                .align(Alignment.TopCenter)
                .offset(y = dimensionResource(R.dimen.book_image_offset))
                .zIndex(2f)
                .clip(
                    RoundedCornerShape(
                        topStart = dimensionResource(R.dimen.book_image_round_top_start),
                        topEnd = 0.dp,
                        bottomEnd = dimensionResource(R.dimen.book_image_round_bottom),
                        bottomStart = dimensionResource(R.dimen.book_image_round_bottom)
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
                    elevation = 8.dp, shape = RoundedCornerShape(dimensionResource(R.dimen.book_image_round_bottom)), clip = true
                ), shape = RoundedCornerShape(dimensionResource(R.dimen.book_image_round_bottom)), colors = CardDefaults.cardColors(
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
                .offset(y = (-dimensionResource(R.dimen.book_image_offset))) // Fixed position below image
                .padding(start = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = book.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
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
        BookCardWithBook(
            Modifier
                .padding(14.dp)
                .height(300.dp),
            PsychologyBooks.booksList[0],
            onBookClicked = {})
    }
}