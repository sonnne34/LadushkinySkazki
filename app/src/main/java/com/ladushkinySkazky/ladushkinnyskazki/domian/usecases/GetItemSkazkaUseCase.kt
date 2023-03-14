package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel
import javax.inject.Inject

class GetItemSkazkaUseCase @Inject constructor(private val skazkyListRepository: SkazkyRepository) {

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyListRepository.getItemSkazka(itemSkazkaId)
    }
}