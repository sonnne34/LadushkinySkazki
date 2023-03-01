package com.ladushkinySkazky.ladushkinnyskazki.domian

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

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

}