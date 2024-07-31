package com.elfak.andjelanikolic.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.models.Result
import com.elfak.andjelanikolic.repositories.AuthRepository
import com.elfak.andjelanikolic.screens.register.RegisterState
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var state by mutableStateOf<LoginState>(LoginState.Idle)

    fun login() {
        viewModelScope.launch {
            state = LoginState.Loading
            state = when (val result = authRepository.login(email, password)) {
                is Result.Success -> {
                    LoginState.Success("Login Succesful")
                }
                is Result.Error -> {
                    LoginState.Error(result.exception)
                }
            }
        }
    }
}