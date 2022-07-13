package com.ladushkinySkazky.ladushkinnyskazki.data

import android.net.Uri
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiModel


data class CategorySkazkiModel(
    var CategoryName: String = String(),
    var CategoryDescription: String = String(),
    var CategoryPicture: String = String(),
    var CategoryPictureUri: Uri? = null
) {

    var isHeader = false
    var Items: HashMap<String, SkazkiModel> = HashMap()

}