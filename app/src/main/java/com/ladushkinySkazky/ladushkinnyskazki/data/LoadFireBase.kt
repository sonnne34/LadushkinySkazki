package com.ladushkinySkazky.ladushkinnyskazki.data

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters.CategoryAdapter
import com.ladushkinySkazky.ladushkinnyskazki.singletons.SkazkiSingleton

class LoadFireBase {
    fun loadSkazki(
        skazkiCatModel: ArrayList<CategorySkazkiModel>,
        categoryAdapter: CategoryAdapter,
        progress: ProgressBar
    ) {

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
//                Toast.makeText(application,
//                    "Проверьте соединение с интернетом",
//                    Toast.LENGTH_LONG)
//                    .show()
            }
        })
    }

    private fun updateAdapter(
        list: ArrayList<CategorySkazkiModel>,
        categoryAdapter: CategoryAdapter,
        progress: ProgressBar
    ) {
        categoryAdapter.setupCategoryAdapter(list)
        progress.visibility = View.GONE
    }
}