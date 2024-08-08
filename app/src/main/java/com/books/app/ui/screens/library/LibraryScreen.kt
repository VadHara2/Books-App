package com.books.app.ui.screens.library

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.books.app.ui.screens.Greeting
import com.books.app.ui.theme.MyApplicationTheme

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    Surface(color = Color(0xFF101010), modifier = Modifier.fillMaxSize()) { }

    Surface(
        color = Color.Transparent,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Library",
            color = Color(0xFFD0006E),
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight(700),
            fontSize = 20.sp
        )


    }
}


@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    MyApplicationTheme {
//        LibraryScreen(
//            navController = rememberNavController(),
//            viewModel = remember {
//                LibraryViewModel()
//            }
//        )
    }
}


