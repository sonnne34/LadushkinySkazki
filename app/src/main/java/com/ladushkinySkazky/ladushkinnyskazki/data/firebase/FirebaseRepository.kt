package com.ladushkinySkazky.ladushkinnyskazki.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ladushkinySkazky.ladushkinnyskazki.data.Mapper
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.AddInteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel

class FirebaseRepository {

    private val mapper = Mapper()

    fun addFeedback(
        feedbackModel: FeedbackModel,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        val mDataBase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Feedback")
        val idFeedback = mDataBase.push().key.toString()
        mDataBase.child(idFeedback)
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
        val mDataBase: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Interactive")
        val idInteractive = mDataBase.push().key.toString()

        val firebaseStorage =
            FirebaseStorage.getInstance().reference.child("Interactive/$idInteractive")
        firebaseStorage.putFile(addInteractiveModel.image)
            .addOnProgressListener { onProgress((100.0 * it.bytesTransferred / it.totalByteCount).toInt()) }
            .addOnSuccessListener {
                mDataBase.child(idInteractive)
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