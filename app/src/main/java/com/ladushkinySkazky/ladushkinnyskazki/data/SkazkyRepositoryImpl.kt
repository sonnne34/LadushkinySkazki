package com.ladushkinySkazky.ladushkinnyskazki.data

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase.LoadInteractive
import com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase.LoadSkazky
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

object SkazkyRepositoryImpl : SkazkyRepository {

    private val skazkyLiveDataFB = LoadSkazky()
    private val skazkyModel = SkazkiCatModel()
    private val skazkyList = mutableListOf<SkazkiCatModel>()

    override fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>> = skazkyLiveDataFB

    private fun getItemCategoryList(position: Int): List<CategorySkazkiModel> {
        return listOf(
            getCategorySkazkyList().value?.get(position)
                ?: throw RuntimeException("Element with id $position not found")
        )
    }

    override fun getItemSkazkyList(position: Int): List<SkazkiCatModel> {
        skazkyList.clear()
        for (category in getItemCategoryList(position)) {
            skazkyModel.isHeader = true
            skazkyModel.CategoryName = category.CategoryName
            skazkyModel.CategoryUriPicture = category.CategoryUriPicture
            skazkyList.add(skazkyModel)
            for (i in category.Items) {
                val textSkazka = i.value.BodySkazka
                val oldValue = "\\n"
                val newValue = "\n"
                val replaceTextSkazka = textSkazka.replace(oldValue, newValue)
                i.value.BodySkazka = replaceTextSkazka
                skazkyList.add(SkazkiCatModel(i.value))
                skazkyList.sortBy { it.Items?.ID }
            }
        }
        return skazkyList
    }

    override fun getItemNewSkazkyList(): List<SkazkiCatModel> {
        skazkyList.clear()
        val list = getCategorySkazkyList().value
        for (cat in list!!) {
            for (i in cat.Items) {
                if (i.value.New) {
                    skazkyList.add(SkazkiCatModel(i.value))
                }
                skazkyList.sortBy { it.Items?.ID }
            }
        }
        return skazkyList
    }

    override fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyList.find { it.Items?.ID == itemSkazkaId }
            ?: throw RuntimeException("Element with id $itemSkazkaId not found")
    }

    override fun getInteractiveList(): LiveData<List<InteractiveModel>> = LoadInteractive()
}
