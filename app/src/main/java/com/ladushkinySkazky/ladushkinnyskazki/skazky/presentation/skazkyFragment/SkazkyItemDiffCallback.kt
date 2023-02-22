package com.ladushkinySkazky.ladushkinnyskazki.skazky.presentation.skazkyFragment

import androidx.recyclerview.widget.DiffUtil
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.SkazkiCatModel

object SkazkyItemDiffCallback : DiffUtil.ItemCallback<SkazkiCatModel>() {

    override fun areItemsTheSame(oldItem: SkazkiCatModel, newItem: SkazkiCatModel): Boolean {
        return oldItem.Items?.NameSkazka == newItem.Items?.NameSkazka
    }

    override fun areContentsTheSame(oldItem: SkazkiCatModel, newItem: SkazkiCatModel): Boolean {
        return oldItem == newItem
    }
}