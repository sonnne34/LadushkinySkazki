package com.ladushkinySkazky.ladushkinnyskazki.loaders

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.ladushkinySkazky.ladushkinnyskazki.adapters.SkazkiAdapter
import com.ladushkinySkazky.ladushkinnyskazki.models.CategorySkazkiModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoadFireBase(context: Context) {

    fun loadSkazki(skazkiCatModel : ArrayList<CategorySkazkiModel>, skazkiAdapter: SkazkiAdapter, progress: ProgressBar) {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Skazka")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val value = ds.getValue(CategorySkazkiModel::class.java)!!

                    Log.d("RRR", "ff = " + value.Items)

                    skazkiCatModel.add(value)
                }
                updateAdapter(skazkiCatModel, skazkiAdapter, progress)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    private fun updateAdapter(list : ArrayList<CategorySkazkiModel>, skazkiAdapter: SkazkiAdapter, progress: ProgressBar) {
        skazkiAdapter.setupSkazkiAdapter(list)
        progress.visibility = View.GONE
    }
}