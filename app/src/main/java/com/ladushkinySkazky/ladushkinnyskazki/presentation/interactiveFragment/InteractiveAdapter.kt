package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel


class InteractiveAdapter(val context: Context) :
    ListAdapter<InteractiveModel, InteractiveAdapter.InteractiveViewHolder>(
        InteractiveItemDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InteractiveViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_list_interactive, parent, false)
        return InteractiveViewHolder(itemView = itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: InteractiveViewHolder, position: Int) {
        val interactiveItem = getItem(position)
        viewHolder.nameAuthor.text = interactiveItem.Name
        viewHolder.yearAuthor.text = ", ${interactiveItem.Year}"
        viewHolder.comment.text = interactiveItem.Comment

        if (interactiveItem.ImageForLoad == null) {
            FirebaseStorage
                .getInstance()
                .getReferenceFromUrl(interactiveItem.Image)
                .downloadUrl.addOnSuccessListener { uri ->
                    interactiveItem.ImageForLoad = uri
                    viewHolder.img.load(uri) {
                        crossfade(true)
                    }
                }
        } else {
            viewHolder.img.load(interactiveItem.ImageForLoad) {
                placeholder(R.drawable.background_image)
                crossfade(true)
            }
        }

        viewHolder.itemView.setOnClickListener {
            InteractiveFullScreenDialog.openFullscreen(context, interactiveItem)
        }
    }

    class InteractiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameAuthor = itemView.findViewById(R.id.txt_name_interactive_item) as TextView
        val yearAuthor = itemView.findViewById(R.id.txt_year_interactive_item) as TextView
        val comment =
            itemView.findViewById(R.id.txt_comment_interactive_item) as TextView
        val img = itemView.findViewById<ImageView>(R.id.img_interactive_item)!!
    }
}
