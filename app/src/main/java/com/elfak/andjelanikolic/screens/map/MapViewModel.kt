package com.elfak.andjelanikolic.screens.map

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.repositories.LocationRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapViewModel(context: Context) : ViewModel() {
    private val locationRepository = LocationRepository(context)
    var currentLocation by mutableStateOf<LatLng?>(null)

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

    private suspend fun fetchCurrentLocation() {
        val location = locationRepository.getCurrentLocation()
        location?.let {
            currentLocation = LatLng(it.latitude, it.longitude)
        }
    }
}