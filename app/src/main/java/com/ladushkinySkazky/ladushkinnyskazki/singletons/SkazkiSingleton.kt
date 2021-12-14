package com.example.testetagi.singletons

import android.util.Log
import com.ladushkinySkazky.ladushkinnyskazki.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.listeners.EventListeners
import com.ladushkinySkazky.ladushkinnyskazki.models.SkazkiModel

object SkazkiSingleton {

    var skazkiItem : ArrayList<CategorySkazkiModel> = ArrayList()
    var listeners: ArrayList<EventListeners> = ArrayList()

    fun addSkazki(item : CategorySkazkiModel){
        var boolean = true
        for(i in skazkiItem){
            if(i.CategoryName == item.CategoryName){
                boolean = false
            }
        }
        if (boolean){
            skazkiItem.add(item)
        }
    }

    fun notifyTwo() {
        for (listener in listeners) {
            listener.updateRR()
            Log.d("Test", "notifiTwo = $listener")
        }
    }

    fun subscribe(listener: EventListeners) {
        listeners.add(listener)
        Log.d("Test", "Listener = $listener")
    }
}