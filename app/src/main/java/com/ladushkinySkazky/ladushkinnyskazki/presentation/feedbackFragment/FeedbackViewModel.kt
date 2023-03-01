package com.ladushkinySkazky.ladushkinnyskazki.presentation.feedbackFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.AddFeedbackUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SkazkyRepositoryImpl
    private val addFeedbackUseCase = AddFeedbackUseCase(repository)

    fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addFeedbackUseCase.addFeedbackUseCase(feedbackModel, onSuccess, onFail)
        }
    }
}