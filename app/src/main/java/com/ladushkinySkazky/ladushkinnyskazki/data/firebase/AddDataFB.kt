package com.ladushkinySkazky.ladushkinnyskazki.data.firebase

import android.text.format.DateFormat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel
import java.util.*
import kotlin.collections.HashMap

class AddDataFB {

    fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        val mDataBase: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Feedback")
        val idNote = mDataBase.push().key.toString()
        val dataTime = DateFormat.format("yyyy-MM-dd hh:mm:ss a", Date()).toString()
        val mapFeedback = HashMap<String, Any>()
        mapFeedback["ID"] = idNote
        mapFeedback["DataTime"] = dataTime
        mapFeedback["NameFeedback"] = "Имя: ${feedbackModel.NameFeedback}"
        mapFeedback["TextFeedback"] = "Отзыв: ${feedbackModel.TextFeedback}"
        mapFeedback["ContactFeedback"] = "Контакт: ${feedbackModel.ContactFeedback}"

        mDataBase.child(idNote)
            .updateChildren(mapFeedback)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFail(it.message.toString()) }
    }
}