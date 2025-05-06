package com.pandawork.bookdiscovery.ui

import androidx.lifecycle.ViewModel
import com.pandawork.bookdiscovery.Tab
import com.pandawork.bookdiscovery.data.BookUiState
import com.pandawork.bookdiscovery.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val bookUiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    fun updateTab(tab: Tab) {
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }

    fun openDialog(dialogState: Boolean, currentBook: Book = Book(0, "", "")) {
        _uiState.update { currentState ->
            currentState.copy(
                currentTab = currentState.currentTab,
                isDialogOpen = dialogState,
                currentBook = currentBook
            )
        }
    }
}
