package com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

class InteractiveAdapter(val context: Context) :
    ListAdapter<InteractiveModel, InteractiveAdapter.InteractiveViewHolder>(
        InteractiveItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InteractiveViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_list_interactive, parent, false)
        return InteractiveViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(viewHolder: InteractiveViewHolder, position: Int) {
        val interactiveItem = getItem(position)
        viewHolder.nameAuthor.text = interactiveItem.Name
        viewHolder.yearAuthor.text = interactiveItem.Year
        viewHolder.comment.text = interactiveItem.Comment
//        LoadImage().loadImageCategorySkazka(context, interactiveItem, holder.categoryPicture)
    }

    class InteractiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameAuthor = itemView.findViewById(R.id.txt_name_interactive_item) as TextView
        var yearAuthor = itemView.findViewById(R.id.txt_year_interactive_item) as TextView
        var comment =
            itemView.findViewById(R.id.txt_comment_interactive_item) as TextView
        var img = itemView.findViewById<ImageView>(R.id.img_interactive_item)!!
    }
}
