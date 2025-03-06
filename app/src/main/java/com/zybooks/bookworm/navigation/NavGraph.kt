package com.zybooks.bookworm

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
    }
}
