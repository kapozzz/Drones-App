package com.example.vozdux.data.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

class FirebaseHelper (
    val database: DatabaseReference,
    val storage: StorageReference
) {
    fun getNewKey(): String {
        return database.push().key!!
    }
}