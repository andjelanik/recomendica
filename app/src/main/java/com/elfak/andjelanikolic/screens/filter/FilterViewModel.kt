package com.elfak.andjelanikolic.screens.filter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FilterViewModel(): ViewModel() {
    var title by mutableStateOf<String?>(null)

    var description by mutableStateOf<String?>(null)

    var radius by mutableStateOf<String?>(null)
}