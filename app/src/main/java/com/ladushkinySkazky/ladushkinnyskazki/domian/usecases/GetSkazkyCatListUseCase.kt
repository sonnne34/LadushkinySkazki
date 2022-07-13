package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyListRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

class GetSkazkyCatListUseCase(private val skazkyListRepository: SkazkyListRepository) {

    fun getSkazkyCatList(): List<SkazkiCatModel> {
        return skazkyListRepository.getSkazkyCatList()
    }
}