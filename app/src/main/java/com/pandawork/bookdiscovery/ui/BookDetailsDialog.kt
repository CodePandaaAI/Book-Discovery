package com.pandawork.bookdiscovery.ui

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.pandawork.bookdiscovery.R
import com.pandawork.bookdiscovery.data.PsychologyBooks
import com.pandawork.bookdiscovery.model.Book
import com.pandawork.bookdiscovery.ui.theme.BookDiscoveryTheme


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
                            Toast.makeText(
                                context,
                                R.string.no_browser_app_found,
                                Toast.LENGTH_SHORT
                            ).show() // Use string resource
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

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
private fun BookDetailsDialogPreview() {
    val context = LocalContext.current
    BookDiscoveryTheme {
        BookDetailsDialog(
            book = PsychologyBooks.getBooksList(context)[0],
            onDismiss = {}
        )
    }
}