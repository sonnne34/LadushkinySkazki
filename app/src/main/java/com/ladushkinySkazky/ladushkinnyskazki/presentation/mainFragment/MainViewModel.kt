package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyListRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetCategoryListUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkazkyListRepositoryImpl
    private val getCategoryListUseCase = GetCategoryListUseCase(repository)
    val categoryList = getCategoryListUseCase.getCategorySkazkyList()
}