package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.AddInteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.AddInteractiveUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InteractiveAddViewModel(private val application: Application) : ViewModel() {

    private val repository = SkazkyRepositoryImpl
    private val addInteractiveUseCase = AddInteractiveUseCase(repository)

    fun isEmptyEditText(edtText: String): String? {
        return if (edtText.isEmpty()) {
            application.resources.getString(R.string.checked_empty)
        } else {
            return null
        }
    }

    fun addInteractive(
        addInteractiveModel: AddInteractiveModel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addInteractiveUseCase.addInteractiveUseCase(
                addInteractiveModel,
                onProgress,
                onSuccess,
                onFailure
            )
        }
    }

}