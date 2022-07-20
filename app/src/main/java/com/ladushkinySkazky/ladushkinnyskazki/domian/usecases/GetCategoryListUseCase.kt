package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository

class GetCategoryListUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>> {
        return skazkyListRepository.getCategorySkazkyList()
    }
}