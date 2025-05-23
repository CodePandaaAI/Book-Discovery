package com.pandawork.bookdiscovery

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pandawork.bookdiscovery.data.BusinessBooks
import com.pandawork.bookdiscovery.data.HealthAndSafetyBooks
import com.pandawork.bookdiscovery.data.MangaAnimeBooks
import com.pandawork.bookdiscovery.data.PsychologyBooks
import com.pandawork.bookdiscovery.model.Book
import com.pandawork.bookdiscovery.ui.BookDetailsDialog
import com.pandawork.bookdiscovery.ui.BookDisplayCard
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
            BookCategory(R.string.psychology_category_title) { ctx ->
                PsychologyBooks.getBooksList(
                    ctx
                )
            },
            BookCategory(R.string.manga_anime_category_title) { ctx ->
                MangaAnimeBooks.getBooksList(
                    ctx
                )
            },
            BookCategory(R.string.business_category_title) { ctx -> BusinessBooks.getBooksList(ctx) },
            BookCategory(R.string.health_safety_category_title) { ctx ->
                HealthAndSafetyBooks.getBooksList(
                    ctx
                )
            }
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

@Preview(showBackground = true)
@Composable
private fun BookHomeScreenPreview() { // Renamed for clarity
    BookDiscoveryTheme {
        BookHomeScreen( /* viewModel = provideFakeViewModelForPreview() */)
    }
}