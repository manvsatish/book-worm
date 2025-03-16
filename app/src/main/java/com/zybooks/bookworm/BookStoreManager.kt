package com.zybooks.bookworm.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zybooks.bookworm.Book
import com.zybooks.bookworm.sampleBooks
import java.io.File
import java.io.InputStreamReader

object BookStorageManager {
    private const val FILE_NAME = "books.json"

    fun saveBooks(context: Context, books: List<Book>) {
        val gson = Gson()
        val jsonString = gson.toJson(books)
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }
    }

    fun loadBooks(context: Context): MutableList<Book> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) {
            saveBooks(context, sampleBooks) // Initialize with sampleBooks if not exists
        }
        return context.openFileInput(FILE_NAME).use { stream ->
            val reader = InputStreamReader(stream)
            val type = object : TypeToken<MutableList<Book>>() {}.type
            Gson().fromJson(reader, type) ?: mutableListOf()
        }
    }
}
