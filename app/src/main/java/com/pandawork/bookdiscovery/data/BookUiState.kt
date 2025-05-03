package com.pandawork.bookdiscovery.data

import com.pandawork.bookdiscovery.Tab

data class BookUiState(
    val currentTab: Tab = Tab.HOME,
    val isDialogOpen: Boolean = false
)