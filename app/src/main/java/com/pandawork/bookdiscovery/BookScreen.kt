package com.pandawork.bookdiscovery

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pandawork.bookdiscovery.data.BusinessBooks
import com.pandawork.bookdiscovery.data.HealthAndSafetyBooks
import com.pandawork.bookdiscovery.data.MangaAnimeBooks
import com.pandawork.bookdiscovery.data.PsychologyBooks
import com.pandawork.bookdiscovery.model.Book
import com.pandawork.bookdiscovery.ui.BookViewModel
import com.pandawork.bookdiscovery.ui.theme.BookDiscoveryTheme

// Consistent naming and type for book list providers
data class BookCategory(
    @StringRes val titleResId: Int,
    val booksProvider: (context: Context) -> List<Book>
)

@Composable
fun BookHomeScreen(bookViewModel: BookViewModel = viewModel()) { // Allow ViewModel injection for testability
    val uiState by bookViewModel.bookUiState.collectAsState()
    val context = LocalContext.current

    // Memoize categories list
    val categories = remember {
        listOf(
            BookCategory(R.string.psychology_category_title) { ctx -> PsychologyBooks.getBooksList(ctx) },
            BookCategory(R.string.manga_anime_category_title) { ctx -> MangaAnimeBooks.getBooksList(ctx) },
            BookCategory(R.string.business_category_title) { ctx -> BusinessBooks.getBooksList(ctx) },
            BookCategory(R.string.health_safety_category_title) { ctx -> HealthAndSafetyBooks.getBooksList(ctx) }
            // Add more categories here
        )
    }

    LazyColumn(
        contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_small))
    ) {
        items(categories) { category ->
            // Memoize books per category, keyed by context and category for efficiency
            val books = remember(context, category) {
                category.booksProvider(context)
            }
            BookCategoryRow(
                categoryTitleResId = category.titleResId,
                books = books, // Renamed for clarity
                onBookClick = { book -> bookViewModel.openDialog(true, book) }
            )
        }
    }

    if (uiState.isDialogOpen) {
        Dialog(onDismissRequest = { bookViewModel.openDialog(false) }) {
            BookDetailsDialog( // Renamed for clarity, implies it's content for a Dialog
                book = uiState.currentBook,
                onDismiss = { bookViewModel.openDialog(false) }
            )
        }
    }
}

@Composable
private fun BookCategoryRow(
    @StringRes categoryTitleResId: Int,
    books: List<Book>,
    onBookClick: (Book) -> Unit, // More specific lambda
    modifier: Modifier = Modifier // Added modifier parameter
) {
    Column(modifier = modifier) { // Added Column to group title and row
        Text(
            text = stringResource(categoryTitleResId),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_large),
                top = dimensionResource(R.dimen.padding_large),
                bottom = dimensionResource(R.dimen.padding_medium) // Added bottom padding for spacing to LazyRow
            )
        )
        // Removed Spacer, padding on Text is enough
        LazyRow {
            items(books) { book ->
                BookDisplayCard(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_small)) // Use horizontal for consistency
                        .height(280.dp), // Consider making this dynamic or passed as a parameter
                    book = book,
                    onCardClicked = { onBookClick(book) }
                )
            }
        }
    }
}

@Composable
fun BookDetailsDialog( // Renamed from BookDetails
    book: Book,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dialogCornerRadius = dimensionResource(R.dimen.padding_small)

    Surface(
        modifier = modifier
            .width(300.dp)
            .clip(RoundedCornerShape(dialogCornerRadius)), // Apply clipping directly to Surface
        shape = RoundedCornerShape(dialogCornerRadius), // Shape for elevation shadow and content clipping
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = book.imageRes),
                contentDescription = book.name, // Good practice
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(
                text = book.name,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.padding_large)))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(R.dimen.padding_small),
                    Alignment.CenterHorizontally
                )
            ) {
                OutlinedButton(onClick = onDismiss) {
                    Text(stringResource(R.string.close_button_text)) // Use string resource
                }
                Button(
                    onClick = {
                        val webIntent = Intent(Intent.ACTION_VIEW, book.link.toUri())
                        if (webIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(webIntent)
                        } else {
                            Toast.makeText(context, R.string.no_browser_app_found, Toast.LENGTH_SHORT).show() // Use string resource
                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = stringResource(R.string.share_button_cd), // Use string resource
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.visit_site_button_text)) // Use string resource
                }
            }
        }
    }
}


@Composable
fun BookDisplayCard(
    modifier: Modifier = Modifier,
    book: Book,
    onCardClicked: () -> Unit,
) {
    Card(
        modifier = modifier
            .width(180.dp) // Consider making this configurable or based on screen size
            .clickable { onCardClicked() },
        shape = RoundedCornerShape(dimensionResource(R.dimen.book_image_round_bottom)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BookImage(
                imageRes = book.imageRes,
                contentDescription = book.name
            )
            BookInfo(
                name = book.name,
                author = book.author
            )
        }
    }
}

@Composable
private fun BookImage(
    @DrawableRes imageRes: Int,
    contentDescription: String?, // Nullable as before, though book.name is non-null
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit, // Or FillHeight/FillWidth depending on desired effect
        modifier = modifier
            .height(190.dp) // Fixed height for image within card
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.padding_small))
            .clip(
                RoundedCornerShape( // Ensure this clipping is visually what you want
                    topStart = dimensionResource(R.dimen.book_image_round_top_start),
                    topEnd = 0.dp // Sharp top-right corner
                )
            )
    )
}

@Composable
private fun BookInfo(
    name: String,
    author: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding( // Consistent padding
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            )
            .fillMaxWidth() // Ensures text ellipsis works if name is long
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small))) // Consider using a consistent small padding/spacing value
        Text(
            text = author,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1, // Added for consistency, in case author names are very long
            overflow = TextOverflow.Ellipsis // Added for consistency
        )
    }
}

// Preview for BookDetailsDialog (previously BookDetailsForPreview)
@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0) // Added background for better visibility
@Composable
private fun BookDetailsDialogPreview() { // Renamed to match the composable it previews
    BookDiscoveryTheme { // Wrap with theme for consistent preview
        BookDetailsDialog(
            book = Book( // Use a sample Book object
                R.drawable.psychology_book_one, // Replace with actual drawable
                "Sample Book Title",
                "Sample Author Name",
                "http://example.com"
            ),
            onDismiss = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun BookHomeScreenPreview() { // Renamed for clarity
    BookDiscoveryTheme {
        // BookAppNavHost might be too complex for a unit preview.
        // Consider previewing BookHomeScreen directly if BookAppNavHost has navigation logic.
        // If BookHomeScreen needs a ViewModel, provide a fake one for the preview.
        BookHomeScreen( /* viewModel = provideFakeViewModelForPreview() */ )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDisplayCardPreview() { // Renamed for clarity
    BookDiscoveryTheme {
        BookDisplayCard(
            modifier = Modifier.padding(16.dp), // Increased padding for preview visibility
            // Provide a real Book object for preview
            book = PsychologyBooks.getBooksList(LocalContext.current).first(),
            onCardClicked = {}
        )
    }
}