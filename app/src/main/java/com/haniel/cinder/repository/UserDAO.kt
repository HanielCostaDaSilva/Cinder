package com.haniel.cinder.repository;

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.haniel.cinder.model.User
import kotlinx.coroutines.tasks.await


class UserDAO {
    val db = FirebaseFirestore.getInstance()

    fun find(callback: (List<User>) -> Unit) {
        db.collection("users").get()
            .addOnSuccessListener { document ->
                val users = document.toObjects<User>()
                callback(users)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun findByName(name: String, callback: (User?) -> Unit) {
        db.collection("users").whereEqualTo("name", name).get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val user = document.documents[0].toObject<User>()
                    if (user != null) {
                        callback(user)
                    }

                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    suspend fun findById(id: String, callback: (User?) -> Unit){ // Mudando para suspender função
        try {
            val document = db.collection("users").whereEqualTo("id", id).get().await()
            document.documents.firstOrNull()?.toObject<User>()
            if (!document.isEmpty) {
                val user = document.documents[0].toObject<User>()
                if (user != null) {
                    callback(user)
                }

            } else {
                callback(null);
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun findById(id: String): User? {
        try {
            val document = db.collection("users").whereEqualTo("id", id).get().await()
            if (document.isEmpty) {
                Log.d("Firestore", "No documents found for ID: $id")
            }
            return document.documents.firstOrNull()?.toObject<User>()

        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching user by ID: $id", e)
            return null
        }
    }



    fun add(user: User, callback: (User?) -> Unit) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                user.id = documentReference.id
                callback(user)
            }
            .addOnFailureListener { e ->
                callback(null)
            }
    }

    fun updateUser(user: User, callback: (Boolean) -> Unit) {
        db.collection("users").document(user.id).set(user)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun deleteUser(id: String, callback: (Boolean) -> Unit) {
        db.collection("users").document(id)
            .delete()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }


}