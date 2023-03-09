package com.ladushkinySkazky.ladushkinnyskazki.domian.usecases

import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel

class AddFeedbackUseCase(private val skazkyListRepository: SkazkyRepository) {

    suspend fun addFeedbackUseCase(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        return skazkyListRepository.addFeedback(feedbackModel, onSuccess, onFail)
    }
}