package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ladushkinySkazky.ladushkinnyskazki.data.InteractiveRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetInteractiveListUseCase

class InteractiveViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = InteractiveRepositoryImpl
    private val getInteractiveListUseCase = GetInteractiveListUseCase(repository)
    val interactiveList = getInteractiveListUseCase.getInteractiveList()
}