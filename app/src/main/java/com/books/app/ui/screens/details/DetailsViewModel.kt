package com.books.app.ui.screens.details


import androidx.lifecycle.ViewModel
import com.books.app.data.model.BookId
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


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
                _state.update { bookDetails }
            }
        }
    }

    private fun loadBookDetails(book: BookId): DetailsState {

        return DetailsState(
            chosenBook = remoteConfigRepository.getBookById(book),
            recommendedBooks = remoteConfigRepository.getRecommendations(),
            allBooks = remoteConfigRepository.getBooksForDetailsCarousel(),
            isLoading = false
        )
    }
}