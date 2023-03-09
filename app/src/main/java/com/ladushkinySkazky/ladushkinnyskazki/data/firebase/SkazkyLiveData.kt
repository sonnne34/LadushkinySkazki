package com.ladushkinySkazky.ladushkinnyskazki.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel

class SkazkyLiveData : LiveData<List<CategorySkazkiModel>>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Skazka")
    private val listener = firebaseDatabase.addValueEventListener(object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {

            value = dataSnapshot.children.map {
                it.getValue(CategorySkazkiModel::class.java) ?: CategorySkazkiModel()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Error", "Error load Firebase Database: $error")
        }
    })

    override fun onActive() {
        super.onActive()
        firebaseDatabase.addValueEventListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        firebaseDatabase.removeEventListener(listener)
    }
}