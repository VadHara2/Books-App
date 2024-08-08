package com.books.app.ui.screens.details

import com.books.app.data.model.Book
import com.books.app.data.model.BookId

data class DetailsState(
    val isLoading: Boolean = true,
    val chosenBook: Book = Book(),
    val recommendedBooks: List<Book> = emptyList(),
    val allBooks: List<Book> = emptyList()
)