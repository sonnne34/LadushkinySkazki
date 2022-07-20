package com.ladushkinySkazky.ladushkinnyskazki.presentation.skazkyFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyListRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetSkazkyListUseCase

class SkazkyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkazkyListRepositoryImpl
    private val getSkazkyListUseCase = GetSkazkyListUseCase(repository)


    private val _skazkyList = MutableLiveData<List<SkazkiCatModel>>()
    val skazkyList: LiveData<List<SkazkiCatModel>>
        get() = _skazkyList

    fun getItemSkazkiList(position: Int) {
        val item = getSkazkyListUseCase.getSkazkyList(position)
        _skazkyList.value = item
    }
}