package com.ladushkinySkazky.ladushkinnyskazki.domian

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.*

interface SkazkyRepository {

    fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>>

    fun getItemSkazkyList(position: Int): List<SkazkiCatModel>

    fun getItemNewSkazkyList(position: Int): List<SkazkiCatModel>

    fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel

    fun getInteractiveList(): LiveData<List<InteractiveModel>>

    suspend fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    suspend fun addInteractive(
        addInteractiveModel: AddInteractiveModel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

}