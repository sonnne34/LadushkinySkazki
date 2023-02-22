package com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.InteractiveModel

class GetInteractiveListUseCase(private val repository: SkazkyRepository) {

    fun getInteractiveList(): LiveData<List<InteractiveModel>> {
        return repository.getInteractiveList()
    }
}