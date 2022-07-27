package com.ladushkinySkazky.ladushkinnyskazki.domian

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

interface InteractiveRepository {

    fun getInteractiveList(): LiveData<List<InteractiveModel>>

}