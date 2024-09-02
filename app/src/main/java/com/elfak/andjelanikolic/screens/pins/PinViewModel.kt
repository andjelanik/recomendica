package com.elfak.andjelanikolic.screens.pins

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.andjelanikolic.calculateDistance
import com.elfak.andjelanikolic.models.Pin
import com.elfak.andjelanikolic.repositories.LocationRepository
import com.elfak.andjelanikolic.repositories.PinRepository
import com.elfak.andjelanikolic.screens.filter.FilterViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class PinViewModel(context: Context, private val filterViewModel: FilterViewModel) : ViewModel() {
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
                var data = it;

                if (currentLocation?.latitude == null || currentLocation?.longitude == null) {
                    fetchCurrentLocation()
                }

                if (filterViewModel.title != null) {
                    data = data.filter { item -> item.title.contains(filterViewModel.title!!) }
                }

                if (filterViewModel.description != null) {
                    data = data.filter { item -> item.description.contains(filterViewModel.description!!) }
                }

                if (filterViewModel.radius != null && filterViewModel.radius!!.isNotEmpty()) {
                    val newData = mutableListOf<Pin>()
                    data.forEach { item ->
                        val radius = filterViewModel.radius!!.toFloat()
                        val distance = calculateDistance(item.latitude.toDouble(), item.longitude.toDouble(), currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
                        if (distance <= radius) {
                            newData.add(item)
                        }
                    }
                    data = newData
                }

                pins = data
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