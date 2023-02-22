package com.ladushkinySkazky.ladushkinnyskazki.presentation.skazkyFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyListRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetNewSkazkyListUseCase
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetSkazkyListUseCase

class SkazkyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkazkyListRepositoryImpl
    private val getSkazkyListUseCase = GetSkazkyListUseCase(repository)
    private val getNewSkazkyListUseCase = GetNewSkazkyListUseCase(repository)


    private val _skazkyList = MutableLiveData<List<SkazkiCatModel>>()
    val skazkyList: LiveData<List<SkazkiCatModel>>
        get() = _skazkyList

    fun getItemSkazkiList(isNewSkazky: Boolean, position: Int) {
        val items = when (isNewSkazky) {
            true -> getNewSkazkyListUseCase.getNewSkazkyList()
            false -> getSkazkyListUseCase.getSkazkyList(position)
        }
        _skazkyList.value = items
    }
}