package com.ladushkinySkazky.ladushkinnyskazki.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

class LoadSkazky : LiveData<List<SkazkiCatModel>>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Skazka")

    private val listener = firebaseDatabase.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            value = dataSnapshot.children.map {
                it.getValue(SkazkiCatModel::class.java) ?: SkazkiCatModel()
            }
        }

        override fun onCancelled(error: DatabaseError) {
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