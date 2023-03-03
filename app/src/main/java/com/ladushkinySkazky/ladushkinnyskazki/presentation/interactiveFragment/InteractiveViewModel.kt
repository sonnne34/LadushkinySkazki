package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import androidx.lifecycle.ViewModel
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetInteractiveListUseCase

class InteractiveViewModel : ViewModel() {
    private val repository = SkazkyRepositoryImpl
    private val getInteractiveListUseCase = GetInteractiveListUseCase(repository)
    val interactiveList = getInteractiveListUseCase.getInteractiveList()
}