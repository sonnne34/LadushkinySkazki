package com.example.ladushkinnyskazki.models

import java.util.HashMap

class CategorySkazkiModel(var CategoryName : String) {
    constructor(): this(CategoryName = String())

    var isHeader = false
    //    var CategoryName: String? = null
    var Items: HashMap<String, SkazkiModel> = HashMap()

}