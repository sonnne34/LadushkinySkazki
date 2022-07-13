package com.ladushkinySkazky.ladushkinnyskazki.domian

import com.ladushkinySkazky.ladushkinnyskazki.data.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiModel

interface SkazkyListRepository {



    fun getCategorySkazkyList(): List<CategorySkazkiModel>

    fun getSkazkyCatList(): List<SkazkiCatModel>

    fun getSkazkyList(): List<SkazkiModel>

    fun getItemSkazka(itemSkazkaId: Int): SkazkiModel

}