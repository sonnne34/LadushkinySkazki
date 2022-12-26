package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyListRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetCategoryListUseCase
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetNewSkazkyListUseCase
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetSkazkyListUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkazkyListRepositoryImpl
    private val getCategoryListUseCase = GetCategoryListUseCase(repository)
    private val getSkazkyListUseCase = GetSkazkyListUseCase(repository)
    val categoryList = getCategoryListUseCase.getCategorySkazkyList()
    private val getNewSkazkyListUseCase = GetNewSkazkyListUseCase(repository)

    private val _categoryUriList = MutableLiveData<List<CategorySkazkiModel>>()
    val categoryUriList: LiveData<List<CategorySkazkiModel>>
        get() = _categoryUriList


    private val _skazkyList = MutableLiveData<List<SkazkiCatModel>>()
    val skazkyList: LiveData<List<SkazkiCatModel>>
        get() = _skazkyList

    fun getItemSkazkiList(position: Int) {
        val item = getSkazkyListUseCase.getSkazkyList(position)
        _skazkyList.value = item
    }

    fun getNewItemSkazkiList() {
        val item = getNewSkazkyListUseCase.getNewSkazkyList()
        _skazkyList.value = item
    }
}