package com.ladushkinySkazky.ladushkinnyskazki.domian

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

interface SkazkyRepository {

    fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>>

    fun getItemSkazkyList(position: Int): List<SkazkiCatModel>

    fun getItemNewSkazkyList(): List<SkazkiCatModel>

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel

    fun getInteractiveList(): LiveData<List<InteractiveModel>>

}