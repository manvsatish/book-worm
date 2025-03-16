package com.zybooks.bookworm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zybooks.bookworm.ui.screens.BookDetailsScreen1
import com.zybooks.bookworm.ui.screens.BookDetailsScreen2
import com.zybooks.bookworm.BookwormApp
import com.zybooks.bookworm.ui.screens.EditBookScreen
import com.zybooks.bookworm.ui.screens.AddBookScreen
import com.zybooks.bookworm.ui.screens.SettingsScreen
import com.zybooks.bookworm.ui.viewmodel.ThemeViewModel

@Composable
fun NavGraph(navController: NavHostController, themeViewModel: ThemeViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { BookwormApp(navController, themeViewModel) }
        composable("addBook") { AddBookScreen(navController, themeViewModel) }  // Updated
        composable("settings") { SettingsScreen(navController, themeViewModel) }
        composable(
            "editBook/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: -1
            EditBookScreen(bookId, navController, themeViewModel)  // Updated
        }
        composable(
            "bookDetailsScreen1/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: -1
            BookDetailsScreen1(bookId, navController, themeViewModel)  // Updated
        }
        composable(
            "bookDetailsScreen2/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: -1
            BookDetailsScreen2(bookId, navController, themeViewModel)  // Updated
        }
    }
}

