package com.ladushkinySkazky.ladushkinnyskazki.data

import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository

object SkazkyListRepositoryImpl : SkazkyListRepository {

    private val categorySkazkyList = mutableListOf<CategorySkazkiModel>()
    private val skazkyCatList = mutableListOf<SkazkiCatModel>()
    private val skazkyList = mutableListOf<SkazkiModel>()

    override fun getCategorySkazkyList(): List<CategorySkazkiModel> {
        return categorySkazkyList.toList()
    }

    override fun getSkazkyCatList(): List<SkazkiCatModel> {
        return skazkyCatList.toList()
    }

    override fun getSkazkyList(): List<SkazkiModel> {
        return skazkyList.toList()
    }

    override fun getItemSkazka(itemSkazkaId: Int): SkazkiModel {
        return skazkyList.find { it.ID == itemSkazkaId }
            ?: throw RuntimeException("Element with id $itemSkazkaId not found")
    }
}