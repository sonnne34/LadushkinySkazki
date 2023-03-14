package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import javax.inject.Inject

class GetInteractiveListUseCase @Inject constructor(private val repository: SkazkyRepository) {

    fun getInteractiveList(): LiveData<List<InteractiveModel>> {
        return repository.getInteractiveList()
    }
}