package com.ladushkinySkazky.ladushkinnyskazki.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.firebase.FirebaseRepository
import com.ladushkinySkazky.ladushkinnyskazki.data.firebase.InteractiveLiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.firebase.SkazkyLiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.*

object SkazkyRepositoryImpl : SkazkyRepository {

    private val mapper = Mapper()
    private val skazkyLiveDataFB = SkazkyLiveData()

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
        val skazkyList = mutableListOf<SkazkiCatModel>()
        return skazkyList.find { it.Items?.ID == itemSkazkaId }
            ?: throw RuntimeException("Element with id $itemSkazkaId not found")
    }

    override fun getInteractiveList(): LiveData<List<InteractiveModel>> =
        MediatorLiveData<List<InteractiveModel>>().apply {
            addSource(InteractiveLiveData()) {
                value = mapper.mapInteractiveToCheckInteractive(it)
            }
        }

    override suspend fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        FirebaseRepository().addFeedback(feedbackModel, onSuccess, onFail)
    }

    override suspend fun addInteractive(
        addInteractiveModel: AddInteractiveModel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        FirebaseRepository().addInteractive(addInteractiveModel, onProgress, onSuccess, onFailure)
    }
}
