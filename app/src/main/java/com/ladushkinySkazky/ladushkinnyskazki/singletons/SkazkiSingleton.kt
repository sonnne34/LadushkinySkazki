package com.ladushkinySkazky.ladushkinnyskazki.singletons

import com.ladushkinySkazky.ladushkinnyskazki.data.CategorySkazkiModel

object SkazkiSingleton {

    var skazkiItem: ArrayList<CategorySkazkiModel> = ArrayList()

    fun addSkazki(item: CategorySkazkiModel) {
        var boolean = true
        for (i in skazkiItem) {
            if (i.CategoryName == item.CategoryName) {
                boolean = false
            }
        }
        if (boolean) {
            skazkiItem.add(item)
        }
    }
}