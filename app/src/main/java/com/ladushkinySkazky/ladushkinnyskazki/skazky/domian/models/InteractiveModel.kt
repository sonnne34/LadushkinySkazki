package com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models

import android.net.Uri

data class InteractiveModel(
    var ID: String = String(),
    var DataTime: String = String(),
    var Check: Boolean = false,
    var Name: String = String(),
    var Year: String = String(),
    var Comment: String = String(),
    var Image: String = String(),
    var ImageForLoad: Uri? = null
)