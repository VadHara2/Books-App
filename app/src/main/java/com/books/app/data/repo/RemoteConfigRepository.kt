package com.books.app.data.repo

import com.books.app.data.model.Banner
import com.books.app.data.model.Book
import com.books.app.data.model.BookId
import com.books.app.data.model.Category
import com.books.app.data.model.Root
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteConfigRepository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : IRemoteConfigRepository {

    private val rootData by lazy { Gson().fromJson(remoteConfig.getString("json_data"), Root::class.java) }
    private val allBooks by lazy { rootData.books }

    override fun getCategories(): List<Category> {
        val categoryNames = allBooks.map { it.genre }.toSet()

        return categoryNames.map { categoryName ->
            Category(
                name = categoryName,
                books = allBooks.filter { book -> book.genre == categoryName }
            )
        }
    }

    override fun getBanners(): List<Banner> {
        return rootData.banners
    }

    override suspend fun fetchAndActivate(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            false
        }
    }

    override fun getBooksForDetailsCarousel(): List<Book> {
        return allBooks
    }

    override fun getRecommendations(): List<Book> {
        return rootData.youWillLikeSection.mapNotNull { recommendedId ->
            allBooks.find { book -> book.id == recommendedId }
        }
    }

    override fun getBookById(id: BookId): Book {
        return allBooks.find { it.id == id } ?: Book()
    }
}