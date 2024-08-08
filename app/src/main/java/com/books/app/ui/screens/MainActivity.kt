package com.books.app.ui.screens

import android.os.Bundle
import android.util.Log
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.books.app.ui.screens.details.DetailsScreen
import com.books.app.data.model.BookId
import com.books.app.ui.screens.library.LibraryScreen
import com.books.app.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

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

                    composable("detailScreen/{bookId}",
                        arguments = listOf(
                            navArgument("bookId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->

                        val bookId = remember {
                            backStackEntry.arguments?.getInt("bookId") ?: 0
                        }
//                        val bookId = backStackEntry.arguments?.getInt("bookId") ?: 5
                        Log.d(TAG, "onCreate: bookId =$bookId")

                        DetailsScreen(bookId = bookId, navController = navController)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}