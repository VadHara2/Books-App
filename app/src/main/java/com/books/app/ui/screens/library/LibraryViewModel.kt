package com.books.app.ui.screens.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LibraryViewModel"

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val remoteConfigRepository: IRemoteConfigRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> = _state.asStateFlow()


    init {
        handleIntent(ScreenIntent.LoadData)
    }

    fun handleIntent(intent: ScreenIntent) {
        when (intent) {
            is ScreenIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {

        _state.value = LibraryState(
            banners = remoteConfigRepository.getBanners(),
            categories = remoteConfigRepository.getCategories(),
            isLoading = false
        )

    }
}