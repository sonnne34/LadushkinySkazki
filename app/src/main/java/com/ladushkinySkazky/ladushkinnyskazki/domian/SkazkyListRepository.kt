package com.ladushkinySkazky.ladushkinnyskazki.domian

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

interface SkazkyListRepository {

    fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>>

    fun getItemSkazkyList(position: Int): List<SkazkiCatModel>

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel

}