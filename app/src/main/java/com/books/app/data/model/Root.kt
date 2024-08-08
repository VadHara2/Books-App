package com.books.app.data.model

import com.google.gson.annotations.SerializedName


data class Root(
    @SerializedName("books") val books: List<Book>
)

