package com.books.app.data.model

import com.google.gson.annotations.SerializedName

typealias BookId = Int
typealias ImageUrl = String

data class Book(
    val author: String = "",
    @SerializedName("cover_url")
    val coverUrl: ImageUrl = "",
    val genre: String = "",
    val id: BookId = 0,
    val likes: String = "",
    val name: String = "",
    val quotes: String = "",
    val summary: String = "",
    val views: String = ""
)