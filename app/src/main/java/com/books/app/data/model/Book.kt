package com.books.app.data.model

import com.google.gson.annotations.SerializedName

data class Book(
    val author: String,
    @SerializedName("cover_url")
    val coverUrl: String,
    val genre: String,
    val id: Int,
    val likes: String,
    val name: String,
    val quotes: String,
    val summary: String,
    val views: String
)