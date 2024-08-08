package com.books.app.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.repo.IRemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(remoteConfigRepository: IRemoteConfigRepository): ViewModel() {
    init {
        viewModelScope.launch {
            remoteConfigRepository.fetchAndActivate()
        }
    }
}