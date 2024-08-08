package com.books.app.data.repo

import com.books.app.data.model.Banner
import com.books.app.data.model.Book
import com.books.app.data.model.Category

interface IRemoteConfigRepository {

    suspend fun fetchAndActivate(): Boolean

    fun getBanners(): List<Banner>

    fun getCategories(): List<Category>
}