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
import com.ladushkinySkazky.ladushkinnyskazki.data.loaders.LoadImage
import com.ladushkinySkazky.ladushkinnyskazki.data.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.data.models.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog.SkazkaBodyDialog

class SkazkiAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var categorySkazkiModel: ArrayList<CategorySkazkiModel>? = null
    private var skazkiCatModelList: ArrayList<SkazkiCatModel> = ArrayList()
    private val LAYOUT_HEADER = 0
    private val LAYOUT_CHILD = 1

    @SuppressLint("NotifyDataSetChanged")
    fun setupSkazkiAdapter(skazkiList: ArrayList<CategorySkazkiModel>) {
        skazkiCatModelList.clear()

        if (categorySkazkiModel == null) {
            categorySkazkiModel = skazkiList
        }

        for (categoryModel in skazkiList) {
            val model = SkazkiCatModel()
            model.CategoryName = categoryModel.CategoryName
            model.isHeader = true
            skazkiCatModelList.add(model)

            for (i in categoryModel.Items) {
                skazkiCatModelList.add(SkazkiCatModel(i.value))
                skazkiCatModelList.sortBy { it.Items?.ID }
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LAYOUT_HEADER) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(R.layout.item_list_category, parent, false)
            HeaderViewHolder(itemView = itemView)

        } else {

            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(R.layout.item_list_skazki, parent, false)
            SkazkiViewHolder(itemView = itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == LAYOUT_HEADER) {
            if (holder is HeaderViewHolder) {
                holder.bindHeader(skazkiCatModel = skazkiCatModelList[position])
            }

        } else {
            if (holder is SkazkiViewHolder) {
                holder.bindSkazki(skazkiCatModel = skazkiCatModelList[position], context = mContext)
            }
        }
    }

    override fun getItemCount(): Int {
        return skazkiCatModelList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return if (skazkiCatModelList[position].isHeader) {
            LAYOUT_HEADER
        } else {
            LAYOUT_CHILD
        }
    }

    class HeaderViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private var category = itemView?.findViewById(R.id.category_item_skazki) as TextView

        fun bindHeader(skazkiCatModel: SkazkiCatModel) {
            category.text = "${skazkiCatModel.CategoryName}"
        }
    }

    class SkazkiViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private var nameSkazka = itemView?.findViewById(R.id.txt_name_skazka) as TextView
        private var descriptionSkazka = itemView?.findViewById(R.id.txt_description_skazka) as TextView
        private var imgSkazka = itemView?.findViewById<ImageView>(R.id.img_name_skazka)

        fun bindSkazki(skazkiCatModel: SkazkiCatModel, context: Context) {

            //FireBase автоматически добавляет "\" к "\n",
            //поэтому заменяю в тексте тег для отработки отступов
            val textSkazka = "${skazkiCatModel.Items?.BodySkazka}"
            val oldValue = "\\n"
            val newValue = "\n"
            val replace = textSkazka.replace(oldValue, newValue)
            skazkiCatModel.Items?.BodySkazka = replace

            nameSkazka.text = "${skazkiCatModel.Items?.NameSkazka}"
            descriptionSkazka.text = "${skazkiCatModel.Items?.DescriptionSkazka}"
            LoadImage().loadImageNameSkazka(context, skazkiCatModel, imgSkazka!!)

            itemView.setOnClickListener {
                SkazkaBodyDialog.openBody(context, skazkiCatModel)
            }

        }

    }
}
