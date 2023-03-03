package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment


import android.app.Application
import androidx.lifecycle.ViewModel
import com.ladushkinySkazky.ladushkinnyskazki.R

class InteractiveAddViewModel(private val application: Application) : ViewModel() {

    fun isEmptyEditText(edtText: String): String? {
        return if (edtText.isEmpty()) {
            application.resources.getString(R.string.checked_empty)
        } else {
            return null
        }
    }

}