package com.pandawork.bookdiscovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pandawork.bookdiscovery.ui.BookViewModel
import com.pandawork.bookdiscovery.ui.theme.BookDiscoveryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookDiscoveryTheme {
                BookAppNavHost(viewModel = viewModel())
            }
        }
    }
}

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
            composable("home") { BookItems() }
            composable("bookmark") { /* Your bookmark screen */ }
            composable("shop") { ShoppingScreen() }
        }
    }
}