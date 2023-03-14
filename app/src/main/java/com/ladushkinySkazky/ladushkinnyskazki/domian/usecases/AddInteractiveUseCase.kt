package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.AddInteractiveModel
import javax.inject.Inject

class AddInteractiveUseCase @Inject constructor(private val skazkyListRepository: SkazkyRepository) {

    suspend fun addInteractiveUseCase(
        addInteractiveModel: AddInteractiveModel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        return skazkyListRepository.addInteractive(
            addInteractiveModel,
            onProgress,
            onSuccess,
            onFailure
        )
    }
}