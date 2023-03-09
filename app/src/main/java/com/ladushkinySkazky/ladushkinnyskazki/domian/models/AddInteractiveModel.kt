package com.ladushkinySkazky.ladushkinnyskazki.domian.models

import android.net.Uri

data class AddInteractiveModel(
    val nameAuthor: String,
    val year: String,
    val comment: String,
    val Check: Boolean = false,
    val image: Uri
)
