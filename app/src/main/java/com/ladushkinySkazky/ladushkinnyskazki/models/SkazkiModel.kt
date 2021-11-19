package com.ladushkinySkazky.ladushkinnyskazki.models

import android.net.Uri

class SkazkiModel(var NameSkazka: String, var DescriptionSkazka: String, var BodySkazka: String, var ImageNameSkazka: String, var ImageNameSkazkaForLoad: Uri?) {
    constructor(): this(NameSkazka = String(), DescriptionSkazka = String(), BodySkazka = String(), ImageNameSkazka = String(), ImageNameSkazkaForLoad = null)
}