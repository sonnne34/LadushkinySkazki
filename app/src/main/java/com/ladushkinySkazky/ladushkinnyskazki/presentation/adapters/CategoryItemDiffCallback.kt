package com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel

class CategoryItemDiffCallback : DiffUtil.ItemCallback<CategorySkazkiModel>() {

    override fun areItemsTheSame(
        oldItem: CategorySkazkiModel,
        newItem: CategorySkazkiModel
    ): Boolean {
        return oldItem.CategoryName == newItem.CategoryName
    }

    override fun areContentsTheSame(
        oldItem: CategorySkazkiModel,
        newItem: CategorySkazkiModel
    ): Boolean {
        return oldItem == newItem
    }
}