package com.elfak.andjelanikolic.screens.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfak.andjelanikolic.startLocationService
import com.elfak.andjelanikolic.stopLocationService
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.secondary
import com.elfak.andjelanikolic.ui.theme.tertiary
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(controller: NavController, topController: NavController) {
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

    LaunchedEffect(Unit) {
        mapViewModel.loadPins()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    compassEnabled = false,
                    mapToolbarEnabled = false,
                )
            ) {
                mapViewModel.currentLocation?.let {
                    Marker(
                        state = rememberMarkerState(position = it)
                    )
                }
                mapViewModel.pins.forEach { pin ->
                    Marker(
                        state = rememberMarkerState(position = LatLng(
                            pin.latitude.toDouble(),
                            pin.longitude.toDouble()
                        ), key = pin.title),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),
                        tag = pin.title,
                        title = pin.title,
                        snippet = pin.description
                    )
                }
            }
            FloatingActionButton(
                containerColor = primary,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    topController.navigate("create_screen/${mapViewModel.currentLocation?.latitude}/${mapViewModel.currentLocation?.longitude}")
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Create",
                    tint = tertiary
                )
            }
        }
    }
}