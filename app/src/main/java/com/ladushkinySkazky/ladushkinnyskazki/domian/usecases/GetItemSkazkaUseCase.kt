package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

class GetItemSkazkaUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyListRepository.getItemSkazka(itemSkazkaId)
    }
}