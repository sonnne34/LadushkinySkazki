package com.ladushkinySkazky.ladushkinnyskazki.domian.models

import android.net.Uri

data class InteractiveModel(
    var ID: Int = Int.MAX_VALUE,
    var Name: String = String(),
    var Year: String = String(),
    var Comment: String = String(),
    var Image: String = String(),
    var ImageForLoad: Uri? = null
)