package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class GetItemSkazkaUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyListRepository.getItemSkazka(itemSkazkaId)
    }
}