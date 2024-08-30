package com.elfak.andjelanikolic.repositories

import android.net.Uri
import com.elfak.andjelanikolic.models.Pin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import com.elfak.andjelanikolic.models.Result
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class PinRepository {
    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val authRepository = AuthRepository()

    suspend fun create(title: String, description: String, photo: Uri?, latitude: Float, longitude: Float): Result<Unit> {
        val user = getUserId()
        if (user != null) {
            try {
                val remote = photo?.let { upload(it) }

                val pin = Pin(
                    title = title,
                    description = description,
                    photo = remote ?: "",
                    latitude = latitude,
                    longitude = longitude,
                    owner = user
                )

                store.collection("pins").add(pin)
                    .await()

                authRepository.points(user, 100)

                return Result.Success(Unit)
            } catch (e: Exception) {
                return Result.Error(e)
            }
        } else {
            return Result.Error(Exception("User not logged in"))
        }
    }

    private fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    private suspend fun upload(photo: Uri): String {
        try {
            val photoRef = storage.reference.child("pin_photos/${photo.lastPathSegment}")
            photoRef.putFile(photo).await()
            return photoRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw RuntimeException("Failed to upload photo", e)
        }
    }

    fun fetch(): Flow<List<Pin>> = callbackFlow {
        val listenerRegistration: ListenerRegistration = store.collection("pins")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                snapshot?.let {
                    val pins = it.documents.mapNotNull { document ->
                        val pin = document.toObject(Pin::class.java)?.apply {
                            uid = document.id
                        }

                        pin
                    }



                    trySend(pins)
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}