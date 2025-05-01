package com.pandawork.bookdiscovery

import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pandawork.bookdiscovery.data.BusinessBooks
import com.pandawork.bookdiscovery.data.HealthAndSafetyBooks
import com.pandawork.bookdiscovery.data.MangaAnimeBooks
import com.pandawork.bookdiscovery.data.PsychologyBooks
import com.pandawork.bookdiscovery.model.Book
import com.pandawork.bookdiscovery.ui.BookViewModel
import com.pandawork.bookdiscovery.ui.theme.BookDiscoveryTheme


@Composable
fun BookHomeScreen(viewModel: BookViewModel = viewModel()) {
    Scaffold(topBar = { TopAppBar() }, bottomBar = { BottomAppBar(viewModel) }) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxWidth()
        ) {
            item {
                BookItems()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
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
        }
    )
}

enum class Tab {
    HOME, BOOKMARK, SHOP
}

@Composable
fun BottomAppBar(viewModel: BookViewModel) {
    val currentTabState by viewModel.bookUiState.collectAsState()
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
                    selected = currentTabState.currentTab == Tab.HOME,
                    selectedIcon = painterResource(R.drawable.baseline_home_24),
                    unselectedIcon = painterResource(R.drawable.outline_home_24),
                    onClick = { viewModel.updateTab(Tab.HOME) })
                Spacer(Modifier.padding(horizontal = 24.dp))
                TabIcon(
                    selected = currentTabState.currentTab == Tab.BOOKMARK,
                    selectedIcon = painterResource(R.drawable.baseline_bookmark_24),
                    unselectedIcon = painterResource(R.drawable.baseline_bookmark_border_24),
                    onClick = { viewModel.updateTab(Tab.BOOKMARK) })
                Spacer(Modifier.padding(horizontal = 24.dp))
                TabIcon(
                    selected = currentTabState.currentTab == Tab.SHOP,
                    selectedIcon = painterResource(R.drawable.baseline_shopping_bag_24),
                    unselectedIcon = painterResource(R.drawable.outline_shopping_bag_24),
                    onClick = { viewModel.updateTab(Tab.SHOP) })
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )

}

@Composable
private fun TabIcon(
    selected: Boolean, selectedIcon: Painter, unselectedIcon: Painter, onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = if (selected) selectedIcon else unselectedIcon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun BookItems(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LazyColumn {
        item {
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
            BookRowCategoryWise(PsychologyBooks.booksList, context)
            Text(
                stringResource(R.string.manga_anime_category_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_large),
                    top = dimensionResource(R.dimen.padding_large)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            BookRowCategoryWise(MangaAnimeBooks.booksList, context)
            Text(
                stringResource(R.string.business_category_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_large),
                    top = dimensionResource(R.dimen.padding_large)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            BookRowCategoryWise(BusinessBooks.booksList, context)

            Text(
                stringResource(R.string.health_safety_category_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_large),
                    top = dimensionResource(R.dimen.padding_large)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            BookRowCategoryWise(HealthAndSafetyBooks.booksList, context)
        }
    }
}

@Composable
private fun BookRowCategoryWise(books: List<Book>, context: Context) {
    LazyRow {
        items(books) { currentBook ->
            BookDisplayCard(
                Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(300.dp), currentBook, onBookClicked = {
                    val link = it.link
                    val webIntent = Intent(Intent.ACTION_VIEW, link.toUri())
                    if (webIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(webIntent)
                    } else {
                        // 🛑 No app found to handle the Intent
                        Toast.makeText(context, "No Browser App Found!", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun BookDisplayCard(
    modifier: Modifier = Modifier,
    currentBook: Book,
    onBookClicked: (Book) -> Unit
) {
    Box(
        modifier = modifier.clickable {
            onBookClicked(currentBook)
        }) {
        // Book Image - positioned first since it affects text placement
        Image(
            painter = painterResource(id = currentBook.imageRes),
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
                    elevation = 8.dp,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.book_image_round_bottom)),
                    clip = true
                ),
            shape = RoundedCornerShape(dimensionResource(R.dimen.book_image_round_bottom)),
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
                .offset(y = (-dimensionResource(R.dimen.book_image_offset))) // Fixed position below image
                .padding(start = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = currentBook.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
            Text(
                text = currentBook.author,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewApp() {
    BookDiscoveryTheme {
        BookHomeScreen()
    }
}

@Preview
@Composable
private fun BookPreview() {
    BookDiscoveryTheme {
        BookDisplayCard(
            Modifier
                .padding(14.dp)
                .height(300.dp),
            PsychologyBooks.booksList[0],
            onBookClicked = {}
        )
    }
}