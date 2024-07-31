package com.elfak.andjelanikolic.screens.register

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val exception: Throwable) : RegisterState()
}