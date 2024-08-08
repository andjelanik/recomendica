package com.elfak.andjelanikolic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.elfak.andjelanikolic.repositories.LocationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationService : Service() {
    private lateinit var locationRepository: LocationRepository
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val serviceScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        locationRepository = LocationRepository(this)

        startForegroundService()
        startLocationUpdates()
    }

    private fun startForegroundService() {
        val channelId = "LocationServiceChannel"
        val channelName = "Location Service"
        val notificationId = 1

        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Location Service")
            .setContentText("Tracking your location")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(notificationId, notification)
    }

    private fun startLocationUpdates() {
        serviceScope.launch {
            locationRepository.getLocationUpdates().collect { location ->
                location?.let {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null) {
                        val locationData = mapOf(
                            "latitude" to it.latitude,
                            "longitude" to it.longitude
                        )
                        withContext(Dispatchers.IO) {
                            firestore.collection("users").document(userId)
                                .update(locationData)
                        }
                    }
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}