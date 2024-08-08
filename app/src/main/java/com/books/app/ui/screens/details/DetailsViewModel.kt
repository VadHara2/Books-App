package com.books.app.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.model.Book
import com.books.app.data.model.BookId
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailsViewModel"
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val remoteConfigRepository: IRemoteConfigRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state

    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.BookClicked -> {
                val bookDetails = loadBookDetails(intent.bookId)
                _state.value = bookDetails
            }
        }
    }

    private fun loadBookDetails(book: BookId): DetailsState {

        val kek = DetailsState(
            chosenBook = remoteConfigRepository.getBookById(book),
            recommendedBooks = remoteConfigRepository.getRecommendations(),
            allBooks = remoteConfigRepository.getBooksForDetailsCarousel(),
            isLoading = false
        )

        Log.d(TAG, "loadBookDetails: $kek")

        return kek
    }
}