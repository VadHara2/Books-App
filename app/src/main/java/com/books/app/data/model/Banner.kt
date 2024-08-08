package com.books.app.data.model

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("book_id")
    val bookId: BookId,
    val cover: ImageUrl,
    val id: Int
)