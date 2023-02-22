package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class GetSkazkyListUseCase(private val skazkyListRepository: SkazkyRepository) {

    fun getSkazkyList(position: Int): List<SkazkiCatModel> {
        return skazkyListRepository.getItemSkazkyList(position)
    }
}