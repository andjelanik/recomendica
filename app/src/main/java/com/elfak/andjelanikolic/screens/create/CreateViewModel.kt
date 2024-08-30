package com.elfak.andjelanikolic.screens.create

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.repositories.PinRepository
import kotlinx.coroutines.launch
import com.elfak.andjelanikolic.models.Result

class CreateViewModel: ViewModel() {
    private val pinRepository = PinRepository()

    var title by mutableStateOf("")

    var description by mutableStateOf("")

    var photo by mutableStateOf<Uri?>(null)

    var state by mutableStateOf<CreateState>(CreateState.Idle)

    fun create(latitude: Float, longitude: Float) {
        viewModelScope.launch {
            state = when (val result = pinRepository.create(title, description, photo, latitude, longitude)) {
                is Result.Success -> {
                    CreateState.Success("Pin created successfully")
                }

                is Result.Error -> {
                    CreateState.Error(result.exception)
                }
            }
        }
    }
}