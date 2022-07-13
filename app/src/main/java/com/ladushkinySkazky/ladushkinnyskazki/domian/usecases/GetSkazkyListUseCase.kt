package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiModel

class GetSkazkyListUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getSkazkyList(): List<SkazkiModel> {
        return skazkyListRepository.getSkazkyList()
    }
}