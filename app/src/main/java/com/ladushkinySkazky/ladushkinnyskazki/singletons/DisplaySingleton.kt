package com.ladushkinySkazky.ladushkinnyskazki.singletons

object DisplaySingleton {
    var sizeWidth: String = ""
    var sizeHead: String = ""

    fun addWidth(mSizeWidth: String) {
        sizeWidth = mSizeWidth
    }

    fun addHead(mSizeHead: String) {
        sizeHead = mSizeHead
    }
}