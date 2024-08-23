package com.elfak.andjelanikolic.screens.leaderboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.models.User
import com.elfak.andjelanikolic.repositories.AuthRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel() : ViewModel() {
    private val authRepository = AuthRepository()

    var users by mutableStateOf<List<User>>(emptyList())

    init {
        viewModelScope.launch {
            authRepository.fetch().collect {
                users = it
            }
        }
    }
}