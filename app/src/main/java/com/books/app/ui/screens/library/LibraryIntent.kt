package com.books.app.ui.screens.library

sealed class ScreenIntent {
    data object LoadData : ScreenIntent()
}