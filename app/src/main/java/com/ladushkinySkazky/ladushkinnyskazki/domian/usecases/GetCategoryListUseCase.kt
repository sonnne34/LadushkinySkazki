package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository

class GetCategoryListUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>> {
        return skazkyListRepository.getCategorySkazkyList()
    }
}