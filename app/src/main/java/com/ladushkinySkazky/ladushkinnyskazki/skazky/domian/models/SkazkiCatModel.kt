package com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models

data class SkazkiCatModel(
    var Items: SkazkiModel? = null,
    var CategoryName: String? = null,
    var CategoryDescription: String? = null,
    var CategoryUriPicture: String? = null,
) {
    var isHeader = false
}