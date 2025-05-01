package com.pandawork.bookdiscovery.ui

import androidx.lifecycle.ViewModel
import com.pandawork.bookdiscovery.Tab
import com.pandawork.bookdiscovery.data.BookUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val bookUiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    fun updateTab(tab: Tab) {
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }
}
