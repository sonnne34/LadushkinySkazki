package com.ladushkinySkazky.ladushkinnyskazki.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ladushkinySkazky.ladushkinnyskazki.data.Mapper
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel

class FirebaseRepository {

    private val mapper = Mapper()

    fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        val mDataBase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Feedback")
        val idNote = mDataBase.push().key.toString()
        mDataBase.child(idNote)
            .updateChildren(mapper.mapFeedbackToDataFirebase(idNote, feedbackModel))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFail(it.message.toString()) }
    }
}