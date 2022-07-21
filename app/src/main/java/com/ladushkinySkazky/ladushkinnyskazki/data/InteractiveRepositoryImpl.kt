package com.ladushkinySkazky.ladushkinnyskazki.data

import androidx.lifecycle.LiveData
import com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase.LoadInteractive
import com.ladushkinySkazky.ladushkinnyskazki.domian.InteractiveRepository
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

object InteractiveRepositoryImpl : InteractiveRepository {
    override fun getInteractiveList(): LiveData<List<InteractiveModel>> = LoadInteractive()

}