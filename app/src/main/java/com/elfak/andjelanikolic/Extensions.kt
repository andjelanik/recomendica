package com.elfak.andjelanikolic

import android.content.Context
import android.content.Intent

fun Context.startLocationService() {
    val intent = Intent(this, LocationService::class.java)
    startForegroundService(intent)
}

fun Context.stopLocationService() {
    val intent = Intent(this, LocationService::class.java)
    stopService(intent)
}