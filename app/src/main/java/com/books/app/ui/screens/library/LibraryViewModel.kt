package com.books.app.ui.screens.library

import androidx.lifecycle.ViewModel
import com.books.app.data.model.BookId
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "LibraryViewModel"

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
            is LibraryIntent.BookClicked -> onBookClicked(intent.bookId)
            is LibraryIntent.ClearNavigation -> clearNavigation()
        }
    }

    private fun loadData() {
        _state.value = LibraryState(
            banners = remoteConfigRepository.getBanners(),
            categories = remoteConfigRepository.getCategories(),
            isLoading = false
        )

    }

    private fun onBookClicked(bookId: BookId) {
        _state.value = _state.value.copy(
            navigationEvent = LibraryState.NavigationEvent.NavigateToDetails(bookId)
        )
    }

    private fun clearNavigation() {
        _state.value = _state.value.copy(navigationEvent = null)
    }
}