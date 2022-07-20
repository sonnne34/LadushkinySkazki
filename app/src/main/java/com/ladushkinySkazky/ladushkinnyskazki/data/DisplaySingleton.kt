package com.ladushkinySkazky.ladushkinnyskazki.data

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