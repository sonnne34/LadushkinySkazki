package com.ladushkinySkazky.ladushkinnyskazki.data

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase.LoadSkazky
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

object SkazkyListRepositoryImpl : SkazkyListRepository {

    private val skazkyListFB = LoadSkazky()
    private val skazkyModel = SkazkiCatModel()
    private val skazkyList = mutableListOf<SkazkiCatModel>()

    override fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>> = skazkyListFB

    private fun getItemCategoryList(position: Int): List<CategorySkazkiModel> {
        return listOf(
            getCategorySkazkyList().value?.get(position)
                ?: throw RuntimeException("Element with id $position not found")
        )
    }

    override fun getItemSkazkyList(position: Int): List<SkazkiCatModel> {
        skazkyList.clear()
        for (category in getItemCategoryList(position)) {
            skazkyModel.CategoryName = category.CategoryName
            skazkyModel.isHeader = true
            skazkyList.add(skazkyModel)
            for (i in category.Items) {
                skazkyList.add(SkazkiCatModel(i.value))
            }
        }
        return skazkyList
    }

    override fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyList.find { it.Items?.ID == itemSkazkaId }
            ?: throw RuntimeException("Element with id $itemSkazkaId not found")
    }
}