package com.ladushkinySkazky.ladushkinnyskazki.domian.models

data class SkazkiModel(
    var ID: Int = Int.MAX_VALUE,
    var NameSkazka: String = String(),
    var DescriptionSkazka: String = String(),
    var BodySkazka: String = String(),
    var SkazkaUriPicture: String = String(),
    var New: Boolean = false
)