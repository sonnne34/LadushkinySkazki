package com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.data.LoadImage
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class CategoryAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context

    private var categorySkazkiModel: ArrayList<CategorySkazkiModel>? = null
    private var skazkiCatModelList: ArrayList<SkazkiCatModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setupCategoryAdapter(skazkiList: ArrayList<CategorySkazkiModel>) {
        skazkiCatModelList.clear()
        if (categorySkazkiModel == null) {
            categorySkazkiModel = skazkiList
        }

        for (categoryModel in skazkiList) {
            val model = SkazkiCatModel()
            model.CategoryName = categoryModel.CategoryName
            model.CategoryPicture = categoryModel.CategoryPicture
            model.CategoryDescription = categoryModel.CategoryDescription
            skazkiCatModelList.add(model)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_category_main_skazki, parent, false)
        return HeaderViewHolder(itemView = itemView)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bindHeader(skazkiCatModel = skazkiCatModelList[position], context = mContext)
        }
    }

    override fun getItemCount(): Int {
        return skazkiCatModelList.count()
    }

    class HeaderViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private var category = itemView?.findViewById(R.id.txt_name_category_skazka) as TextView
        private var categoryDescription = itemView?.findViewById<TextView>(R.id.txt_description_category)
        private var categoryPicture = itemView?.findViewById<ImageView>(R.id.img_category_skazka)

        fun bindHeader(skazkiCatModel: SkazkiCatModel, context: Context) {
            category.text = "${skazkiCatModel.CategoryName}"
            categoryDescription?.text = "${skazkiCatModel.CategoryDescription}"
            LoadImage().loadImageCategorySkazka(context, skazkiCatModel, categoryPicture!!)
        }
    }
}
