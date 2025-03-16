package com.zybooks.bookworm
import kotlinx.serialization.Serializable

data class Book(
    val id: Int,
    var title: String,
    var author: String,
    var imageUrl: String,
    var userRating: Float,
    val dateAdded: String,
    var review: String,
    var totalPages: Int,
    var pagesRead: Int,
    var genre: String,
    var description: String,
    var authorBio: String,
    var userReview: String
)
