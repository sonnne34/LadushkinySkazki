package com.ladushkinySkazky.ladushkinnyskazki.data.models

import android.net.Uri


class CategorySkazkiModel(
    var CategoryName: String,
    var CategoryDescription: String,
    var CategoryPicture: String,
    var CategoryPictureUri: Uri?
) {
    constructor() : this(
        CategoryName = String(),
        CategoryDescription = String(),
        CategoryPicture = String(),
        CategoryPictureUri = null
    )

    var isHeader = false
    var Items: HashMap<String, SkazkiModel> = HashMap()

}