package com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.domian.model.SkazkiCatModel
import com.squareup.picasso.Picasso

class CategoryAdapter :
    ListAdapter<SkazkiCatModel, CategoryAdapter.CategoryViewHolder>(CategoryItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_category_main_skazki, parent, false)
        return CategoryViewHolder(itemView = itemView)

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = getItem(position)
        holder.category.text = categoryItem.CategoryName
        holder.categoryDescription.text = categoryItem.CategoryDescription
        Picasso.get().load(categoryItem.CategoryPicture).into(holder.categoryPicture)
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category = itemView.findViewById(R.id.txt_name_category_skazka) as TextView
        var categoryDescription =
            itemView.findViewById<TextView>(R.id.txt_description_category)!!
        var categoryPicture = itemView.findViewById<ImageView>(R.id.img_category_skazka)!!

    }
}
