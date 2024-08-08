package com.books.app.ui.screens.library

import com.books.app.data.model.Banner
import com.books.app.data.model.Book
import com.books.app.data.model.Category

data class LibraryState(
    val banners: List<Banner> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true
)
