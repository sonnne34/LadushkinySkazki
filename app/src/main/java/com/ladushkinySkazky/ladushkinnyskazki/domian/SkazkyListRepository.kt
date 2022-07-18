package com.ladushkinySkazky.ladushkinnyskazki.domian

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiModel

interface SkazkyListRepository {

    fun getCategorySkazkyList(): LiveData<List<SkazkiCatModel>>

    fun getSkazkyCatList(): LiveData<List<SkazkiCatModel>>

    fun getItemSkazkyList(position: Int): SkazkiCatModel

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel

}