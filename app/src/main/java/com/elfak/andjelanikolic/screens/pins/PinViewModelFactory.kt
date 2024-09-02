package com.elfak.andjelanikolic.screens.pins

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elfak.andjelanikolic.screens.filter.FilterViewModel

class PinViewModelFactory(private val application: Context, private val filterViewModel: FilterViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PinViewModel(application, filterViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}