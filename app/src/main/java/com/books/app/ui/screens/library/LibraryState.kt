package com.books.app.ui.screens.library

import com.books.app.data.model.Banner
import com.books.app.data.model.Book
import com.books.app.data.model.BookId
import com.books.app.data.model.Category

data class LibraryState(
    val banners: List<Banner> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val navigationEvent: NavigationEvent? = null
){
    sealed class NavigationEvent {
        data class NavigateToDetails(val bookId: BookId) : NavigationEvent()
    }
}
