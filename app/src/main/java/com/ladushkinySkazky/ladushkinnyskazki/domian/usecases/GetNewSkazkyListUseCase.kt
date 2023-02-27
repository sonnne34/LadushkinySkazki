package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class GetNewSkazkyListUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getNewSkazkyList(): List<SkazkiCatModel> {
        return skazkyListRepository.getItemNewSkazkyList()
    }
}