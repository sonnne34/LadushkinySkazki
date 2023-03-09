package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

class GetInteractiveListUseCase(private val repository: SkazkyRepository) {

    fun getInteractiveList(): LiveData<List<InteractiveModel>> {
        return repository.getInteractiveList()
    }
}