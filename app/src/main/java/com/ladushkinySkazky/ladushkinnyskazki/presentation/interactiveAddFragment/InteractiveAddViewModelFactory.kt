package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InteractiveAddViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InteractiveAddViewModel::class.java)) {
            return InteractiveAddViewModel(application) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}