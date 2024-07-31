package com.elfak.andjelanikolic.screens.login

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val message: String) : LoginState()
    data class Error(val exception: Throwable) : LoginState()
}