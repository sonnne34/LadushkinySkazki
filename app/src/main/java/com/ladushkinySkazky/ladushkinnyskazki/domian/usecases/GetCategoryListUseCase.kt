package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.data.CategorySkazkiModel

class GetCategoryListUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getCategorySkazkyList(): List<CategorySkazkiModel> {
        return skazkyListRepository.getCategorySkazkyList()
    }
}