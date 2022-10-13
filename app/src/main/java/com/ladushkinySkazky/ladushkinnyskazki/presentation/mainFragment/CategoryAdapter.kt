package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase.LoadImage
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel

class CategoryAdapter(val context: Context) :
    ListAdapter<CategorySkazkiModel, CategoryAdapter.CategoryViewHolder>(CategoryItemDiffCallback()) {

    var onCategoryClickListener: OnCategoryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_category_main_skazki, parent, false)
        return CategoryViewHolder(itemView = itemView)

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = getItem(position)
        holder.category.text = categoryItem.CategoryName
        holder.categoryDescription.text = categoryItem.CategoryDescription
        LoadImage(context, holder.categoryPicture).loadImageCategorySkazka(categoryItem)
        holder.itemView.setOnClickListener {
            onCategoryClickListener?.onCategoryClick(categoryItem, position)
        }
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category = itemView.findViewById(R.id.txt_name_category_skazka) as TextView
        var categoryDescription =
            itemView.findViewById<TextView>(R.id.txt_description_category)!!
        var categoryPicture = itemView.findViewById<ImageView>(R.id.img_category_skazka)!!
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(categorySkazkiModel: CategorySkazkiModel, position: Int)
    }
}
