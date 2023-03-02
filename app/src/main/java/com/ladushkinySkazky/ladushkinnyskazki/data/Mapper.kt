package com.ladushkinySkazky.ladushkinnyskazki.data

import android.text.format.DateFormat
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel
import java.util.*

class Mapper {

    fun mapCategoryListToSkazkyCatList(categoryList: List<CategorySkazkiModel>): List<SkazkiCatModel> {
        val skazkyList = mutableListOf<SkazkiCatModel>()
        val skazkyModel = SkazkiCatModel()
        for (category in categoryList) {
            skazkyModel.isHeader = true
            skazkyModel.CategoryName = category.CategoryName
            skazkyModel.CategoryUriPicture = category.CategoryUriPicture
            skazkyList.add(skazkyModel)
            for (i in category.Items) {
                val text = i.value.BodySkazka
                i.value.BodySkazka = replaceTextSkazka(text)
                skazkyList.add(SkazkiCatModel(i.value))
                skazkyList.sortBy { it.Items?.ID }
            }
        }
        return skazkyList
    }

    fun mapSkazkyCatListToNewSkazkyCatList(categoryList: List<CategorySkazkiModel>): List<SkazkiCatModel> {
        val skazkyList = mapCategoryListToSkazkyCatList(categoryList)
        val newSkazkyList = mutableListOf<SkazkiCatModel>()
        for (i in skazkyList) {
            if (i.Items?.New == true) {
                newSkazkyList.add(SkazkiCatModel(i.Items))
            }
        }
        return newSkazkyList
    }

    private fun replaceTextSkazka(textSkazka: String): String {
        val oldValue = "\\n"
        val newValue = "\n"
        return textSkazka.replace(oldValue, newValue)
    }

    fun mapInteractiveToCheckInteractive(
        interactiveList: List<InteractiveModel>,
    ): List<InteractiveModel> =
        interactiveList.filter { it.Check }.sortedByDescending { it1 -> it1.DataTime }

    fun mapFeedbackToDataFirebase(
        idNote: String,
        feedbackModel: FeedbackModel,
    ): HashMap<String, Any> {
        val mapFeedback = HashMap<String, Any>()
        mapFeedback["ID"] = idNote
        mapFeedback["DataTime"] = currentDataTime()
        mapFeedback["NameFeedback"] = "Имя: ${feedbackModel.NameFeedback}"
        mapFeedback["TextFeedback"] = "Отзыв: ${feedbackModel.TextFeedback}"
        mapFeedback["ContactFeedback"] = "Контакт: ${feedbackModel.ContactFeedback}"
        return mapFeedback
    }

    private fun currentDataTime(): String {
        return DateFormat.format("yyyy-MM-dd hh:mm:ss a", Date()).toString()
    }

}