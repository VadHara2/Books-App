package com.books.app.ui.screens.library

import androidx.lifecycle.ViewModel
import com.books.app.data.model.BookId
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val remoteConfigRepository: IRemoteConfigRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> = _state.asStateFlow()

    init {
        handleIntent(LibraryIntent.LoadData)
    }

    fun handleIntent(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.LoadData -> loadData()
            is LibraryIntent.BookClicked -> navigateToBookDetails(intent.bookId)
            is LibraryIntent.ClearNavigation -> clearNavigationEvent()
        }
    }

    private fun loadData() {
        _state.update { currentState ->
            currentState.copy(
                banners = remoteConfigRepository.getBanners(),
                categories = remoteConfigRepository.getCategories(),
                isLoading = false
            )
        }
    }

    private fun navigateToBookDetails(bookId: BookId) {
        _state.update { currentState ->
            currentState.copy(
                navigationEvent = LibraryState.NavigationEvent.NavigateToDetails(bookId)
            )
        }
    }

    private fun clearNavigationEvent() {
        _state.update { currentState ->
            currentState.copy(navigationEvent = null)
        }
    }
}