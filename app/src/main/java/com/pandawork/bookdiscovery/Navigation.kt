package com.pandawork.bookdiscovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pandawork.bookdiscovery.ui.BookViewModel

@Composable
fun BookAppNavHost(viewModel: BookViewModel = viewModel()) {
    val navController = rememberNavController()

    // Listen to currentTab and navigate
    val uiState by viewModel.bookUiState.collectAsState()

    LaunchedEffect(uiState.currentTab) {
        when (uiState.currentTab) {
            Tab.HOME -> navController.navigate("home") {
                popUpTo("home") { inclusive = false }
            }
            Tab.BOOKMARK -> navController.navigate("bookmark") {
                popUpTo("home") { inclusive = false }
            }
            Tab.SHOP -> navController.navigate("shop") {
                popUpTo("home") { inclusive = false }
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = { BottomAppBar(viewModel) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { BookHomeScreen() }
            composable("bookmark") { /* Your bookmark screen */ }
            composable("shop") { ShoppingScreen() }
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