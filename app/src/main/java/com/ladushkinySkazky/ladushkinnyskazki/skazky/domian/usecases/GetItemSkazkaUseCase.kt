package com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.SkazkiCatModel

class GetItemSkazkaUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyListRepository.getItemSkazka(itemSkazkaId)
    }
}