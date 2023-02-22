package com.ladushkinySkazky.ladushkinnyskazki.skazky.presentation.interactiveFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ladushkinySkazky.ladushkinnyskazki.skazky.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.usecases.GetInteractiveListUseCase

class InteractiveViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SkazkyRepositoryImpl
    private val getInteractiveListUseCase = GetInteractiveListUseCase(repository)
    val interactiveList = getInteractiveListUseCase.getInteractiveList()
}