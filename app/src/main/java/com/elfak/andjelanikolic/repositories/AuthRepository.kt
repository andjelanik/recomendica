package com.elfak.andjelanikolic.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import com.elfak.andjelanikolic.models.Result
import com.elfak.andjelanikolic.models.User

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
                val user = User(username, email, phone, name, url)
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


    fun logout() {
        this.auth.signOut()
    }
}