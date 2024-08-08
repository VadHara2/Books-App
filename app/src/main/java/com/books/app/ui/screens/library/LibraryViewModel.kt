package com.books.app.ui.screens.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LibraryViewModel"

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val remoteConfigRepository: IRemoteConfigRepository
) : ViewModel() {


}