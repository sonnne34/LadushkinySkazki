package com.ladushkinySkazky.ladushkinnyskazki.domian.model

import android.net.Uri

data class SkazkiModel(
    var ID: Int  = Int.MAX_VALUE,
    var NameSkazka: String = String(),
    var DescriptionSkazka: String = String(),
    var BodySkazka: String = String(),
    var ImageNameSkazka: String= String(),
    var ImageNameSkazkaForLoad: Uri? = null
)