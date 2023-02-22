package com.ladushkinySkazky.ladushkinnyskazki.skazky.presentation.mainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ladushkinySkazky.ladushkinnyskazki.skazky.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.usecases.GetCategoryListUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkazkyRepositoryImpl
    private val getCategoryListUseCase = GetCategoryListUseCase(repository)
    val categoryList = getCategoryListUseCase.getCategorySkazkyList()

}