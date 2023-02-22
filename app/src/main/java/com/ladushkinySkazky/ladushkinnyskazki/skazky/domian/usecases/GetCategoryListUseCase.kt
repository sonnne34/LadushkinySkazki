package com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.SkazkyRepository

class GetCategoryListUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>> {
        return skazkyListRepository.getCategorySkazkyList()
    }
}