package com.elfak.andjelanikolic.screens.pins

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.models.Pin
import com.elfak.andjelanikolic.repositories.LocationRepository
import com.elfak.andjelanikolic.repositories.PinRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class PinViewModel(context: Context) : ViewModel() {
    private val locationRepository = LocationRepository(context)
    private val pinRepository = PinRepository()

    var pins by mutableStateOf<List<Pin>>(emptyList())

    var currentLocation by mutableStateOf<LatLng?>(null)
        private set

    init {
        viewModelScope.launch {
            fetchCurrentLocation()
            locationRepository.getLocationUpdates()
                .collect { location ->
                    location?.let {
                        currentLocation = LatLng(it.latitude, it.longitude)
                    }
                }
        }
    }

    fun loadPins() {
        viewModelScope.launch {
            pinRepository.fetch().collect {
                pins = it
            }
        }
    }

    private suspend fun fetchCurrentLocation() {
        try {
            val location = locationRepository.getCurrentLocation()
            location?.let {
                currentLocation = LatLng(it.latitude, it.longitude)
            }
        } catch (_: SecurityException) { }
    }

}