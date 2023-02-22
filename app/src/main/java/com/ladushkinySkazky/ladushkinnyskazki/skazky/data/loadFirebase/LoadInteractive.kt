package com.ladushkinySkazky.ladushkinnyskazki.skazky.data.loadFirebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.InteractiveModel

class LoadInteractive : LiveData<List<InteractiveModel>>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Interactive")

    private val listener = firebaseDatabase.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.children.map {
                it.getValue(InteractiveModel::class.java) ?: InteractiveModel()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Error", "Error load Firebase Database: $error")
        }
    })

    override fun onActive() {
        firebaseDatabase.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        firebaseDatabase.removeEventListener(listener)
        super.onInactive()
    }
}