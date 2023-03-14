package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel
import javax.inject.Inject

class GetNewSkazkyListUseCase @Inject constructor(private val skazkyListRepository: SkazkyRepository) {

    fun getNewSkazkyList(position: Int): List<SkazkiCatModel> {
        return skazkyListRepository.getItemNewSkazkyList(position)
    }
}