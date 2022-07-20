package com.ladushkinySkazky.ladushkinnyskazki.domian.models

import android.net.Uri

data class SkazkiCatModel(
    var Items: SkazkiModel? = null,
    var CategoryName: String? = null,
    var CategoryDescription: String? = null,
    var CategoryPicture: String? = null,
    var CategoryPictureUri: Uri? = null
) {
    var isHeader = false
}