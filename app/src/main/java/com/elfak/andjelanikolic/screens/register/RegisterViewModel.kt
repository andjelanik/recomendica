package com.elfak.andjelanikolic.screens.register

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.models.Result
import com.elfak.andjelanikolic.repositories.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    var username by mutableStateOf("")

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var fullName by mutableStateOf("")

    var phone by mutableStateOf("")

    var photo by mutableStateOf<Uri?>(null)

    var state by mutableStateOf<RegisterState>(RegisterState.Idle)

    fun register() {
        viewModelScope.launch {
            state = RegisterState.Loading
            state = when (val result = authRepository.register(username, email, password, fullName, phone, photo)) {
                is Result.Success -> {
                    RegisterState.Success("Registration Succesful")
                }
                is Result.Error -> {
                    RegisterState.Error(result.exception)
                }
            }
        }
    }
}