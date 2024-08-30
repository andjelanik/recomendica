package com.elfak.andjelanikolic.screens.create

sealed class CreateState {
    data object Idle : CreateState()
    data object Loading : CreateState()
    data class Success(val message: String) : CreateState()
    data class Error(val exception: Throwable) : CreateState()
}