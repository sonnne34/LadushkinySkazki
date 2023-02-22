package com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.SkazkiCatModel

class GetSkazkyListUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getSkazkyList(position: Int): List<SkazkiCatModel> {
        return skazkyListRepository.getItemSkazkyList(position)
    }
}