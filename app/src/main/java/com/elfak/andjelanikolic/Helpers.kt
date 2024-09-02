package com.elfak.andjelanikolic

import android.location.Location

fun calculateDistance(startLat: Double, startLon: Double, endLat: Double, endLon: Double): Float {
    val results = FloatArray(1)
    Location.distanceBetween(startLat,startLon,endLat,endLon,results)
    return results[0]
}