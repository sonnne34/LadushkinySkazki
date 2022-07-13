package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.loaders.LoadFireBase
import com.ladushkinySkazky.ladushkinnyskazki.data.models.CategorySkazkiModel

class MainFragmentModel(application: Application): AndroidViewModel(application) {

    private var _categoryList = MutableLiveData<CategorySkazkiModel>()
    val categoryList: LiveData<CategorySkazkiModel>
        get() = _categoryList

    fun loadCategory(){

    }
}