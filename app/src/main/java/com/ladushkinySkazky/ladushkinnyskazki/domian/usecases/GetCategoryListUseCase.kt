package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

class GetCategoryListUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getCategorySkazkyList(): LiveData<List<SkazkiCatModel>> {
        return skazkyListRepository.getCategorySkazkyList()
    }
}