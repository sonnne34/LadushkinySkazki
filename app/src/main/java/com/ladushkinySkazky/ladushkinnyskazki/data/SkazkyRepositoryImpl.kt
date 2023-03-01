package com.ladushkinySkazky.ladushkinnyskazki.data

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.firebase.AddDataFB
import com.ladushkinySkazky.ladushkinnyskazki.data.firebase.InteractiveLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.firebase.SkazkyLiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

object SkazkyRepositoryImpl : SkazkyRepository {

    private val skazkyLiveDataFB = SkazkyLiveData()
    private val skazkyList = mutableListOf<SkazkiCatModel>()
    private val mapper = SkazkyMapper()

    override fun getCategorySkazkyList(): LiveData<List<CategorySkazkiModel>> = skazkyLiveDataFB

    private fun getItemCategoryList(position: Int): List<CategorySkazkiModel> {
        return listOf(
            getCategorySkazkyList().value?.get(position)
                ?: throw RuntimeException("Element with id $position not found")
        )
    }

    override fun getItemSkazkyList(position: Int): List<SkazkiCatModel> {
        return mapper.mapCategoryListToSkazkyCatList(getItemCategoryList(position))
    }

    override fun getItemNewSkazkyList(position: Int): List<SkazkiCatModel> {
        return mapper.mapSkazkyCatListToNewSkazkyCatList(getItemCategoryList(position))
    }

    override fun getItemSkazka(itemSkazkaId: Int): SkazkiCatModel {
        return skazkyList.find { it.Items?.ID == itemSkazkaId }
            ?: throw RuntimeException("Element with id $itemSkazkaId not found")
    }

    override fun getInteractiveList(): LiveData<List<InteractiveModel>> = InteractiveLiveData()
    override suspend fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        AddDataFB().addFeedback(feedbackModel, onSuccess, onFail)
    }
}
