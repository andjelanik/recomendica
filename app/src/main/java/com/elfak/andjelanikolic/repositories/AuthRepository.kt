package com.elfak.andjelanikolic.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import com.elfak.andjelanikolic.models.Result
import com.elfak.andjelanikolic.models.User
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AuthRepository {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    suspend fun register(username: String, email: String, password: String, name: String, phone: String, photo: Uri?): Result<Unit> {
        try {
            val result = this.auth.createUserWithEmailAndPassword(email, password).await()
            var url = ""

            val id = result?.user?.uid
            if (id != null && photo != null) {
                val avatar = storage.reference.child("avatars/$id.jpg")
                avatar.putFile(photo).await()
                url = avatar.downloadUrl.await().toString()
            }

            result.user?.let {
                val user = User(username, email, phone, name, url, 0f, 0f, 0)
                store.collection("users").document(it.uid).set(user).await()
            }

            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        try {
            this.auth.signInWithEmailAndPassword(email, password).await()
            return Result.Success(Unit)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    fun current(): String? {
        val user = this.auth.currentUser
        user?.let {
            return user.email
        }
        return null
    }

    suspend fun getUserInfo(): Result<User?> {
        return try {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val snapshot = store.collection("users").document(userId).get().await()
                val user = snapshot.toObject(User::class.java)
                Result.Success(user)
            } else {
                Result.Error(Exception("No user is currently logged in"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun fetch(): Flow<List<User>> = callbackFlow {
        val query = store.collection("users")
            .orderBy("points", Query.Direction.DESCENDING)

        val listenerRegistration: ListenerRegistration = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }

            snapshot?.let {
                val users = it.documents.mapNotNull { document ->
                    document.toObject(User::class.java)
                }
                trySend(users)
            }
        }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun logout() {
        this.auth.signOut()
    }
}