package com.ladushkinySkazky.ladushkinnyskazki.domian.model

import android.net.Uri


data class CategorySkazkiModel(
    var CategoryName: String = String(),
    var CategoryDescription: String = String(),
    var CategoryPicture: String = String(),
    var CategoryPictureUri: Uri? = null
) {

    var isHeader = false
    var Items: HashMap<String, SkazkiModel> = HashMap()

}