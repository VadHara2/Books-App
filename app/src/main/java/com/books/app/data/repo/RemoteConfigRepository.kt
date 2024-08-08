package com.books.app.data.repo

import android.util.Log
import com.books.app.data.model.Banner
import com.books.app.data.model.Book
import com.books.app.data.model.Category
import com.books.app.data.model.Root
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "RemoteConfigRepository"

@Singleton
class RemoteConfigRepository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : IRemoteConfigRepository {

    private fun root() = Gson().fromJson(remoteConfig.getString("json_data"), Root::class.java)

    override fun getCategories(): List<Category> {
        val allBooks = root().books
        val categoryNames = allBooks.map { it.genre }.toSet()

        val categories = categoryNames.toList().map { categoryName ->
            Category(
                name = categoryName,
                books = allBooks.filter { book -> book.genre == categoryName }
            )
        }

        return categories
    }

    override fun getBanners(): List<Banner> {

        return root().banners
    }

    override suspend fun fetchAndActivate(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            Log.d(TAG, "fetchAndActivate: $e")
            false
        }
    }

}