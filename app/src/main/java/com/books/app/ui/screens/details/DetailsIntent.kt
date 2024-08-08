package com.books.app.ui.screens.details

import com.books.app.data.model.BookId

sealed class DetailsIntent {
    data class BookClicked(val bookId: BookId) : DetailsIntent()
}