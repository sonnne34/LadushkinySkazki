package com.ladushkinySkazky.ladushkinnyskazki.domian.models


data class CategorySkazkiModel(
    var CategoryName: String = String(),
    var CategoryDescription: String = String(),
    var CategoryUriPicture: String = String(),
) {

    var isHeader = false
    var Items: HashMap<String, SkazkiModel> = HashMap()

}