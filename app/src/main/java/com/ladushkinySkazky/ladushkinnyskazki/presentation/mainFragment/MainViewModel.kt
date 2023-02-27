package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetCategoryListUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkazkyRepositoryImpl
    private val getCategoryListUseCase = GetCategoryListUseCase(repository)
    val categoryList = getCategoryListUseCase.getCategorySkazkyList()

}