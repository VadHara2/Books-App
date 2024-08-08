package com.books.app.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SplashViewModel"

@HiltViewModel
class SplashViewModel @Inject constructor(remoteConfigRepository: IRemoteConfigRepository): ViewModel() {
    init {
        viewModelScope.launch {
            val b = remoteConfigRepository.fetchAndActivate()
            Log.d(TAG, "b: $b")
        }
    }
}