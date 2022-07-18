package com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel

class CategoryItemDiffCallback : DiffUtil.ItemCallback<SkazkiCatModel>() {

    override fun areItemsTheSame(oldItem: SkazkiCatModel, newItem: SkazkiCatModel): Boolean {
        return oldItem.CategoryName == newItem.CategoryName
    }

    override fun areContentsTheSame(oldItem: SkazkiCatModel, newItem: SkazkiCatModel): Boolean {
        return oldItem == newItem
    }
}