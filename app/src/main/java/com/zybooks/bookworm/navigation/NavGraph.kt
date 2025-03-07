package com.zybooks.bookworm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zybooks.bookworm.ui.screens.BookDetailsScreen
import com.zybooks.bookworm.ui.screens.BookDetailsScreen2
import com.zybooks.bookworm.BookwormApp
import com.zybooks.bookworm.EditBookScreen
import com.zybooks.bookworm.ui.screens.AddBookScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { BookwormApp(navController) }
        composable("addBook") { AddBookScreen(navController) }
        composable("editBook") { EditBookScreen(navController) }
        composable(
            "bookDetails/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: -1
            BookDetailsScreen(bookId, navController)
        }
        // Add a new composable for BookDetailsScreen2
        composable(
            "bookDetailsScreen2/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: -1
            BookDetailsScreen2(bookId, navController)
        }
    }
}
