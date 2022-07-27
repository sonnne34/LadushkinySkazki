package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.InteractiveRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

class GetInteractiveListUseCase(private val interactiveRepository: InteractiveRepository) {

    fun getInteractiveList(): LiveData<List<InteractiveModel>> {
        return interactiveRepository.getInteractiveList()
    }
}