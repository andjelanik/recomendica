package com.elfak.andjelanikolic.screens.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfak.andjelanikolic.startLocationService
import com.elfak.andjelanikolic.stopLocationService
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(controller: NavController) {
    val cameraPositionState = rememberCameraPositionState()

    val context = LocalContext.current.applicationContext
    val viewModelFactory = MapViewModelFactory(context)
    val mapViewModel: MapViewModel = viewModel(factory = viewModelFactory)
    context.startLocationService()

    LaunchedEffect(mapViewModel.currentLocation) {
        mapViewModel.currentLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            mapViewModel.currentLocation?.let {
                Marker(
                    state = rememberMarkerState(position = it),
                    title = "You are here",
                    snippet = "Current location"
                )
            }
        }
    }
}