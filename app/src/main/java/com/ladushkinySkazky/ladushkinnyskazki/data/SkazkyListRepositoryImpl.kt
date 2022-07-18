package com.ladushkinySkazky.ladushkinnyskazki.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

object SkazkyListRepositoryImpl : SkazkyListRepository {

    private val skazkyCatList = MutableLiveData<List<SkazkiCatModel>>()
    private val skazkyList = mutableListOf<SkazkiCatModel>()
    private val skazkyListTwo = mutableListOf<List<SkazkiCatModel>>()

    private val skazkyListFB = LoadSkazky()

    override fun getCategorySkazkyList(): LiveData<List<SkazkiCatModel>> = skazkyListFB

    override fun getSkazkyCatList(): LiveData<List<SkazkiCatModel>> {
        return skazkyCatList
    }



    override fun getItemSkazkyList(position: Int): SkazkiCatModel {
        return getCategorySkazkyList().value?.get(position)
            ?: throw RuntimeException("Element with id $position not found")
    }




    override fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyList.find { it.Items?.ID == itemSkazkaId }
            ?: throw RuntimeException("Element with id $itemSkazkaId not found")
    }
}