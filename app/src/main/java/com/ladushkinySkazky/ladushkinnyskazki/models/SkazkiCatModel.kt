package com.ladushkinySkazky.ladushkinnyskazki.models

import android.net.Uri

class SkazkiCatModel(var Items: SkazkiModel? = null, var CategoryName: String? = null, var CategoryDescription: String? = null, var CategoryPicture : String? = null, var CategoryPictureUri: Uri? = null)  {
    var isHeader = false
}