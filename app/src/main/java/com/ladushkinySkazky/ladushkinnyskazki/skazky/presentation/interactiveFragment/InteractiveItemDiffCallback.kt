package com.ladushkinySkazky.ladushkinnyskazki.skazky.presentation.interactiveFragment

import androidx.recyclerview.widget.DiffUtil
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.InteractiveModel

object InteractiveItemDiffCallback : DiffUtil.ItemCallback<InteractiveModel>() {

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