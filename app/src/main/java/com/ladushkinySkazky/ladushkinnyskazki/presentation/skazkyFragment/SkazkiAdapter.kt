package com.ladushkinySkazky.ladushkinnyskazki.presentation.skazkyFragment

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
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel
import com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog.SkazkaTextDialog

class SkazkiAdapter(val context: Context) :
    ListAdapter<SkazkiCatModel, RecyclerView.ViewHolder>(SkazkyItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_CATEGORY) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(R.layout.item_list_category, parent, false)
            CategorySkazkyViewHolder(itemView = itemView)

        } else {

            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(R.layout.item_list_skazki, parent, false)
            SkazkiViewHolder(itemView = itemView)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val skazkaItem = getItem(position)

        if (viewHolder.itemViewType == VIEW_TYPE_CATEGORY) {
            if (viewHolder is CategorySkazkyViewHolder) {
                viewHolder.category.text = "${skazkaItem.CategoryName}"
            }

        } else {
            if (viewHolder is SkazkiViewHolder) {
                skazkaItem.Items?.BodySkazka = "${skazkaItem.Items?.BodySkazka}"
                viewHolder.nameSkazka.text = "${skazkaItem.Items?.NameSkazka}"
                viewHolder.descriptionSkazka.text = "${skazkaItem.Items?.DescriptionSkazka}"
                viewHolder.imgSkazka.load(skazkaItem.Items?.SkazkaUriPicture) {
                    crossfade(true)
                    placeholder(R.drawable.background_image)
                }
                if (skazkaItem.Items!!.New) {
                    viewHolder.imgNew.visibility = View.VISIBLE
                }
                viewHolder.itemView.setOnClickListener {
                    SkazkaTextDialog.openBody(context, skazkaItem)
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.CategoryName != null) {
            VIEW_TYPE_CATEGORY
        } else {
            VIEW_TYPE_SKAZKA
        }
    }

    class CategorySkazkyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category = itemView.findViewById(R.id.category_item_skazki) as TextView
    }

    class SkazkiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameSkazka = itemView.findViewById(R.id.txt_name_skazka) as TextView
        var descriptionSkazka =
            itemView.findViewById(R.id.txt_description_skazka) as TextView
        var imgSkazka = itemView.findViewById<ImageView>(R.id.img_name_skazka)!!
        var imgNew = itemView.findViewById<ImageView>(R.id.img_new_skazka)!!
        var imgEye = itemView.findViewById<ImageView>(R.id.img_eye_skazka)!!
        var imgLike = itemView.findViewById<ImageView>(R.id.img_like_skazka)!!
    }

    companion object {
        const val VIEW_TYPE_CATEGORY = 100
        const val VIEW_TYPE_SKAZKA = 101
//        const val VIEW_YES = "view yes"
//        const val VIEW_NO = "view no"
//        const val LIKE_YES = "like yes"
//        const val LIKE_NO = "like no"
    }
}
