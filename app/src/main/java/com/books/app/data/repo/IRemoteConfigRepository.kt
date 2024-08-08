package com.books.app.data.repo

import com.books.app.data.model.Book

interface IRemoteConfigRepository {

    suspend fun fetchAndActivate(): Boolean

    fun getBooks(): List<Book>

    fun getBanners()
}