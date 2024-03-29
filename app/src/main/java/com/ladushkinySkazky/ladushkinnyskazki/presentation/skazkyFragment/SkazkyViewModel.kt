package com.ladushkinySkazky.ladushkinnyskazki.presentation.skazkyFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetNewSkazkyListUseCase
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetSkazkyListUseCase

class SkazkyViewModel : ViewModel() {

    private val repository = SkazkyRepositoryImpl
    private val getSkazkyListUseCase = GetSkazkyListUseCase(repository)
    private val getNewSkazkyListUseCase = GetNewSkazkyListUseCase(repository)
    private val _skazkyList = MutableLiveData<List<SkazkiCatModel>>()
    val skazkyList: LiveData<List<SkazkiCatModel>>
        get() = _skazkyList

    fun getItemSkazkiList(isNewSkazky: Boolean, position: Int) {
        val items = when (isNewSkazky) {
            true -> getNewSkazkyListUseCase.getNewSkazkyList(position)
            false -> getSkazkyListUseCase.getSkazkyList(position)
        }
        _skazkyList.value = items
    }
}