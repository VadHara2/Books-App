package com.books.app.ui.screens.library

import com.books.app.data.model.BookId

sealed class LibraryIntent {
    data object LoadData : LibraryIntent()
    data class BookClicked(val bookId: BookId) : LibraryIntent()
    data object ClearNavigation: LibraryIntent()
}