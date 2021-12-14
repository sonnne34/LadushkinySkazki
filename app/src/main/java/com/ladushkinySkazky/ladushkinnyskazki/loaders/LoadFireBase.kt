package com.ladushkinySkazky.ladushkinnyskazki.loaders

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.testetagi.singletons.SkazkiSingleton
import com.ladushkinySkazky.ladushkinnyskazki.models.CategorySkazkiModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ladushkinySkazky.ladushkinnyskazki.adapters.CategoryAdapter

class LoadFireBase() {
    fun loadSkazki(skazkiCatModel : ArrayList<CategorySkazkiModel>, categoryAdapter: CategoryAdapter, progress: ProgressBar) {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Skazka")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val value = ds.getValue(CategorySkazkiModel::class.java)!!
                    Log.d("RRR", "ff = " + value.Items)

                    skazkiCatModel.add(value)
                    SkazkiSingleton.addSkazki(value)
                }
                updateAdapter(skazkiCatModel, categoryAdapter, progress)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    private fun updateAdapter(list : ArrayList<CategorySkazkiModel>, categoryAdapter: CategoryAdapter, progress: ProgressBar) {
        categoryAdapter.setupCategoryAdapter(list)
        progress.visibility = View.GONE
    }
}