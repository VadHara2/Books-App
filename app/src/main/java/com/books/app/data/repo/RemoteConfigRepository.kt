package com.books.app.data.repo

import android.util.Log
import com.books.app.data.model.Book
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


    override fun getBooks(): List<Book> {
        val root = Gson().fromJson(remoteConfig.getString("json_data"), Root::class.java)
        return root.books
    }

    override fun getBanners() {
        TODO("Not yet implemented")
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