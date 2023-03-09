package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import androidx.lifecycle.ViewModel
import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.usecases.GetCategoryListUseCase

class MainViewModel : ViewModel() {

    private val repository = SkazkyRepositoryImpl
    private val getCategoryListUseCase = GetCategoryListUseCase(repository)
    val categoryList = getCategoryListUseCase.getCategorySkazkyList()

}