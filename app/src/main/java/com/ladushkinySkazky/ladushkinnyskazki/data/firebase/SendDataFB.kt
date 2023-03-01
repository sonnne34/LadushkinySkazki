package com.ladushkinySkazky.ladushkinnyskazki.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SendDataFB {

    fun sendFeedback(
        dataTime: String,
        name: String,
        text: String,
        contact: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        val mDataBase: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Feedback")
        val idNote = mDataBase.push().key.toString()
        val mapFeedback = HashMap<String, Any>()
        mapFeedback["ID"] = idNote
        mapFeedback["DataTime"] = dataTime
        mapFeedback["NameFeedback"] = name
        mapFeedback["TextFeedback"] = text
        mapFeedback["ContactFeedback"] = contact

        mDataBase.child(idNote)
            .updateChildren(mapFeedback)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFail(it.message.toString()) }
    }
}