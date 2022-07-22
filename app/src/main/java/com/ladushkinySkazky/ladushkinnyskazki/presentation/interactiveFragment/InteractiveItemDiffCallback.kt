package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import androidx.recyclerview.widget.DiffUtil
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

class InteractiveItemDiffCallback : DiffUtil.ItemCallback<InteractiveModel>() {

    override fun areItemsTheSame(
        oldItem: InteractiveModel,
        newItem: InteractiveModel
    ): Boolean {
        return oldItem.ID == newItem.ID
    }

    override fun areContentsTheSame(
        oldItem: InteractiveModel,
        newItem: InteractiveModel
    ): Boolean {
        return oldItem == newItem
    }
}