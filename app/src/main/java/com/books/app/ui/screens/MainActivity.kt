package com.books.app.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.books.app.ui.screens.details.DetailsScreen
import com.books.app.ui.screens.library.LibraryScreen
import com.books.app.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "libraryScreen"
                ) {

                    composable("libraryScreen") {
                        LibraryScreen(navController = navController)
                    }

                    composable(
                        "detailScreen/{bookId}",
                        arguments = listOf(navArgument("bookId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
                        DetailsScreen(bookId = bookId, navController = navController)
                    }

                }

            }
        }
    }
}