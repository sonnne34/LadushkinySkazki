package com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class LoadImage {

    @SuppressLint("CheckResult")
    fun loadImageNameSkazka(
        context: Context,
        skazkiCatModel: SkazkiCatModel,
        imageName: ImageView
    ) {

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

    @SuppressLint("CheckResult")
    fun loadImageCategorySkazka(
        context: Context,
        skazkiCatModel: CategorySkazkiModel,
        imageName: ImageView
    ) {

        val glide = Glide.with(context)

        if (skazkiCatModel.CategoryPictureUri == null) {

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(skazkiCatModel.CategoryPicture)

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                skazkiCatModel.CategoryPictureUri = uri
                val img = glide.load(uri)
                img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                img.centerCrop().into(imageName)
            }
        } else {
            val img = glide.load(skazkiCatModel.CategoryPictureUri)
            img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            img.centerCrop().into(imageName)
        }
    }

    @SuppressLint("CheckResult")
    fun loadImageInteractive(
        context: Context,
        model: InteractiveModel,
        imageName: ImageView
    ) {

        val glide = Glide.with(context)

        if (model.ImageForLoad == null) {

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(model.Image)

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                model.ImageForLoad = uri
                val img = glide.load(uri)
                img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                img.centerCrop().into(imageName)
            }
        } else {
            val img = glide.load(model.ImageForLoad)
            img.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            img.centerCrop().into(imageName)
        }
    }
}