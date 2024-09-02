package com.elfak.andjelanikolic.screens.map

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elfak.andjelanikolic.screens.filter.FilterViewModel

class MapViewModelFactory(private val application: Context, private val filterViewModel: FilterViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(application, filterViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}