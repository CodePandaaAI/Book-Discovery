package com.pandawork.bookdiscovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pandawork.bookdiscovery.data.BookUiState
import com.pandawork.bookdiscovery.data.BusinessBooks
import com.pandawork.bookdiscovery.data.HealthAndSafetyBooks
import com.pandawork.bookdiscovery.data.MangaAnimeBooks
import com.pandawork.bookdiscovery.data.PsychologyBooks
import com.pandawork.bookdiscovery.model.Book
import com.pandawork.bookdiscovery.ui.BookViewModel
import com.pandawork.bookdiscovery.ui.theme.BookDiscoveryTheme

@Composable
fun BookHomeScreen() {
    val appContext = LocalContext.current
    val bookViewModel: BookViewModel = viewModel()
    val bookUiState by bookViewModel.bookUiState.collectAsState()
    LazyColumn {
        item {
            BookRowCategoryWise(
                R.string.psychology_category_title,
                PsychologyBooks.getBooksList(appContext)
            )
            BookRowCategoryWise(
                R.string.manga_anime_category_title,
                MangaAnimeBooks.getBooksList(appContext)
            )
            BookRowCategoryWise(
                R.string.business_category_title,
                BusinessBooks.getBooksList(appContext)
            )
            BookRowCategoryWise(
                R.string.health_safety_category_title,
                HealthAndSafetyBooks.getBooksList(appContext)
            )
        }
    }
    if (bookUiState.isDialogOpen == true) {
        Dialog(onDismissRequest = { bookViewModel.openDialog(false) }) {
            BookDetails(bookUiState)
        }
    }
}

@Composable
fun BookDetails(bookUiState: BookUiState) {
    Surface(
        Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .clip(
                RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = bookUiState.currentBook.imageRes),
                contentDescription = "Book cover",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .width(160.dp)
                    .height(200.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = dimensionResource(R.dimen.book_image_round_top_start),
                            topEnd = 0.dp,
                            bottomEnd = dimensionResource(R.dimen.book_image_round_bottom),
                            bottomStart = dimensionResource(R.dimen.book_image_round_bottom)
                        )
                    )
            )
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {}) { Text("dud") }
                Button(onClick = {}) { }
            }
        }
    }
}

@Composable
private fun BookRowCategoryWise(categoryTitleResId: Int, bookList: List<Book>) {
    val bookViewModel: BookViewModel = viewModel()
    Text(
        stringResource(categoryTitleResId),
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            top = dimensionResource(R.dimen.padding_large)
        )
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
    LazyRow {
        items(bookList) { book ->
            BookDisplayCard(
                Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(280.dp),
                book,
                onCardClicked = {
                    bookViewModel.openDialog(true, book)
//                    val link = currentBook.link
//                    val webIntent = Intent(Intent.ACTION_VIEW, link.toUri())
//                    if (webIntent.resolveActivity(appContext.packageManager) != null) {
//                        appContext.startActivity(webIntent)
//                    } else {
//                        // 🛑 No app found to handle the Intent
//                        Toast.makeText(appContext, "No Browser App Found!", Toast.LENGTH_SHORT).show()
//                    }
                }
            )
        }
    }
}

@Composable
fun BookDisplayCard(
    modifier: Modifier = Modifier,
    book: Book,
    onCardClicked: () -> Unit,
) {
    Box(
        modifier = modifier.clickable {
            onCardClicked()
        }) {
        // Book Image - positioned first since it affects text placement
        Image(
            painter = painterResource(id = book.imageRes),
            contentDescription = "Book cover",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .width(150.dp)
                .height(190.dp)
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

@Composable
fun BookDetailsForPreview() {
    Box(
        Modifier.background(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        Image(painterResource(R.drawable.back), contentDescription = null)
        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Book Image - positioned first since it affects text placement
            Image(
                painter = painterResource(id = R.drawable.psychology_book_one),
                contentDescription = "Book cover",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .width(120.dp)
                    .height(160.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = dimensionResource(R.dimen.book_image_round_top_start),
                            topEnd = 0.dp,
                            bottomEnd = dimensionResource(R.dimen.book_image_round_bottom),
                            bottomStart = dimensionResource(R.dimen.book_image_round_bottom)
                        )
                    )
            )
            Spacer(Modifier.height(8.dp))
            Row {
                Button(onClick = {}) { Text("dud") }
                Button(onClick = {}) { }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewApp() {
    BookDiscoveryTheme {
        BookAppNavHost()
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
            PsychologyBooks.getBooksList(LocalContext.current)[0],
            onCardClicked = {}
        )
    }
}

@Preview
@Composable
private fun BookDetailsPreview() {
    BookDetailsForPreview()
}