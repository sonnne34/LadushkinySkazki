package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyListRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.data.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetCategoryListUseCase
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetSkazkyCatListUseCase
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetSkazkyListUseCase

class SkazkyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkazkyListRepositoryImpl

    private val getCategoryListUseCase = GetCategoryListUseCase(repository)
    private val getSkazkyCatUseCase = GetSkazkyCatListUseCase(repository)
    private val getSkazkyListUseCase = GetSkazkyListUseCase(repository)

    val categoryList = MutableLiveData<List<CategorySkazkiModel>>()
    val skazkyCatList = MutableLiveData<List<SkazkiCatModel>>()
    val skazkyList = MutableLiveData<List<SkazkiModel>>()

    fun getCategorySkazkyList() {
        val list = getCategoryListUseCase.getCategorySkazkyList()
        categoryList.value = list
    }

    fun getSkazkyCatList() {
        val list = getSkazkyCatUseCase.getSkazkyCatList()
        skazkyCatList.value = list
    }

    fun getSkazkyList() {
        val list = getSkazkyListUseCase.getSkazkyList()
        skazkyList.value = list
    }
}