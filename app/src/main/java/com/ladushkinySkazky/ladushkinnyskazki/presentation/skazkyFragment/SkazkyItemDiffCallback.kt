package com.ladushkinySkazky.ladushkinnyskazki.presentation.skazkyFragment

import androidx.recyclerview.widget.DiffUtil
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

object SkazkyItemDiffCallback : DiffUtil.ItemCallback<SkazkiCatModel>() {

    override fun areItemsTheSame(oldItem: SkazkiCatModel, newItem: SkazkiCatModel): Boolean {
        return oldItem.Items?.NameSkazka == newItem.Items?.NameSkazka
    }

    override fun areContentsTheSame(oldItem: SkazkiCatModel, newItem: SkazkiCatModel): Boolean {
        return oldItem == newItem
    }
}