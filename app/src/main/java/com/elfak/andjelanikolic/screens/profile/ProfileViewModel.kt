package com.elfak.andjelanikolic.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.models.Result
import com.elfak.andjelanikolic.repositories.AuthRepository
import com.elfak.andjelanikolic.models.User
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private val authRepository = AuthRepository()

    var user by mutableStateOf<User?>(null)
    var isServiceRunning by mutableStateOf(true)

    init {
        viewModelScope.launch {
            when (val result = authRepository.getUserInfo()) {
                is Result.Success -> {
                    user = result.data
                }
                else -> { }
            }
        }
    }

    fun logOut() {
        authRepository.logout()
    }
}