package com.ladushkinySkazky.ladushkinnyskazki.skazky.presentation.mainFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.CategorySkazkiModel

class CategoryAdapter(val context: Context) :
    ListAdapter<CategorySkazkiModel,
            CategoryAdapter.CategoryViewHolder>(CategoryItemDiffCallback) {

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
        holder.categoryPicture.load(categoryItem.CategoryUriPicture) {
            placeholder(R.drawable.background_image)
            crossfade(true)
        }
        holder.itemView.setOnClickListener {
            onCategoryClickListener?.onCategoryClick(categoryItem, position)
        }
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category = itemView.findViewById(R.id.txt_name_category_skazka) as TextView
        val categoryDescription =
            itemView.findViewById(R.id.txt_description_category) as TextView
        val categoryPicture = itemView.findViewById(R.id.img_category_skazka) as ImageView
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(categorySkazkiModel: CategorySkazkiModel, position: Int)
    }
}
