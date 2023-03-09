package com.ladushkinySkazky.ladushkinnyskazki.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ladushkinySkazky.ladushkinnyskazki.data.INTERACTIVE_SK
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

class InteractiveLiveData : LiveData<List<InteractiveModel>>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference(INTERACTIVE_SK)

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