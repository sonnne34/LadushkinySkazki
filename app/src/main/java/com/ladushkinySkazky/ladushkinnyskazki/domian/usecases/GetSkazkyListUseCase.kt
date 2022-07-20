package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class GetSkazkyListUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getSkazkyList(position: Int): List<SkazkiCatModel> {
        return skazkyListRepository.getItemSkazkyList(position)
    }
}