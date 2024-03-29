package com.ladushkinySkazky.ladushkinnyskazki.data.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ladushkinySkazky.ladushkinnyskazki.data.Mapper
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.AddInteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel
import com.ladushkinySkazky.ladushkinnyskazki.data.FEEDBACK_SK
import com.ladushkinySkazky.ladushkinnyskazki.data.INTERACTIVE_SK

class FirebaseRepository {

    private val mapper = Mapper()
    private var dataBaseInstance = FirebaseDatabase.getInstance()

    fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        val dataBaseFeedback = dataBaseInstance.getReference(FEEDBACK_SK)
        val idFeedback = dataBaseFeedback.push().key.toString()
        dataBaseFeedback.child(idFeedback)
            .updateChildren(mapper.mapFeedbackToDataFirebase(idFeedback, feedbackModel))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFail(it.message.toString()) }
    }

    fun addInteractive(
        addInteractiveModel: AddInteractiveModel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val dataBaseInteractive = dataBaseInstance.getReference(INTERACTIVE_SK)
        val idInteractive = dataBaseInteractive.push().key.toString()

        val firebaseStorage =
            FirebaseStorage.getInstance().reference.child("$INTERACTIVE_SK/$idInteractive")
        firebaseStorage.putFile(addInteractiveModel.image)
            .addOnProgressListener { onProgress((100.0 * it.bytesTransferred / it.totalByteCount).toInt()) }
            .addOnSuccessListener {
                dataBaseInteractive.child(idInteractive)
                    .updateChildren(
                        mapper.mapInteractiveToDataFirebase(
                            idInteractive,
                            addInteractiveModel
                        )
                    )
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it.message.toString()) }
            }
            .addOnFailureListener { onFailure(it.message.toString()) }
    }
}