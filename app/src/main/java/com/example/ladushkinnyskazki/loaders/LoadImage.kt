package com.example.ladushkinnyskazki.loaders

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ladushkinnyskazki.models.SkazkiCatModel
import com.google.firebase.storage.FirebaseStorage

class LoadImage {

    @SuppressLint("CheckResult")
    fun loadImageNameSkazka(context: Context, skazkiCatModel: SkazkiCatModel, imageName: ImageView) {

        val glide = Glide.with(context)

        if (skazkiCatModel.Items?.ImageNameSkazkaForLoad == null) {

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(skazkiCatModel.Items?.ImageNameSkazka!!)

            storageRef.downloadUrl.addOnSuccessListener { uri ->

                skazkiCatModel.Items?.ImageNameSkazkaForLoad = uri
                val img = glide.load(uri)
                img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                img.centerCrop().into(imageName)
            }
        } else {

            val img = glide.load(skazkiCatModel.Items?.ImageNameSkazkaForLoad)
            img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            img.centerCrop().into(imageName)
        }
    }

    fun loadImageBodySkazka(context: Context, skazkiCatModel: SkazkiCatModel, imageName: ImageView) {


        val glide = Glide.with(context)

        if (skazkiCatModel.Items?.ImageNameSkazkaForLoad == null) {

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(skazkiCatModel.Items?.ImageNameSkazka!!)

            storageRef.downloadUrl.addOnSuccessListener { uri ->

                skazkiCatModel.Items?.ImageNameSkazkaForLoad = uri
                val img = glide.load(uri)
                img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                img.centerCrop().into(imageName)
            }
        } else {

            val img = glide.load(skazkiCatModel.Items?.ImageNameSkazkaForLoad)
            img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            img.centerCrop().into(imageName)
        }
    }
}