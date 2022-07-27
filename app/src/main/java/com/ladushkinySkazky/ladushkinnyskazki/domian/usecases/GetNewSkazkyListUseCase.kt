package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class GetNewSkazkyListUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getNewSkazkyList(): List<SkazkiCatModel> {
        return skazkyListRepository.getItemNewSkazkyList()
    }
}