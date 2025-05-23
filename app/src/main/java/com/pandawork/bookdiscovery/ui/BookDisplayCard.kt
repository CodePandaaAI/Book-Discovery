package com.pandawork.bookdiscovery.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pandawork.bookdiscovery.R
import com.pandawork.bookdiscovery.data.PsychologyBooks
import com.pandawork.bookdiscovery.model.Book
import com.pandawork.bookdiscovery.ui.theme.BookDiscoveryTheme

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
                imageRes = book.imageRes, contentDescription = book.name
            )
            BookInfo(
                name = book.name, author = book.author
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
    name: String, author: String, modifier: Modifier = Modifier
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

@Preview(showBackground = true)
@Composable
private fun BookDisplayCardPreview() { // Renamed for clarity
    BookDiscoveryTheme {
        BookDisplayCard(
            modifier = Modifier.padding(16.dp), // Increased padding for preview visibility
            // Provide a real Book object for preview
            book = PsychologyBooks.getBooksList(LocalContext.current).first(), onCardClicked = {})
    }
}