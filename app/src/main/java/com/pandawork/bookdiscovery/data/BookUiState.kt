package com.pandawork.bookdiscovery.data

import androidx.compose.ui.platform.LocalContext
import com.pandawork.bookdiscovery.Tab
import com.pandawork.bookdiscovery.model.Book

data class BookUiState(
    val currentTab: Tab = Tab.HOME,
    val isDialogOpen: Boolean = false,
    val currentBook: Book = Book(0, "", "")
)