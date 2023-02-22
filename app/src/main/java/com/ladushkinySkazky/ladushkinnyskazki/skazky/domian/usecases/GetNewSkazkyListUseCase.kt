package com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.SkazkiCatModel

class GetNewSkazkyListUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getNewSkazkyList(): List<SkazkiCatModel> {
        return skazkyListRepository.getItemNewSkazkyList()
    }
}