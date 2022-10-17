package com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

class LoadImage(
    private val context: Context,
    private val imageView: ImageView
) {
    private val storage = FirebaseStorage.getInstance()

    @SuppressLint("CheckResult")
    fun loadImageCategorySkazka(categorySkazkiModel: CategorySkazkiModel) {

        var saveUri = categorySkazkiModel.CategoryPictureUri
        Log.d("LoadUri", "на входе loadImageCategorySkazka saveUriSave = $saveUri")

        if (saveUri == null) {
            storage.getReferenceFromUrl(categorySkazkiModel.CategoryPicture)
                .downloadUrl
                .addOnSuccessListener { uri ->
                    saveUri = uri

                    Log.d(
                        "LoadUri",
                        "пошла загрузка в loadImageCategorySkazka saveUriSave = $saveUri"
                    )
                    glideCenterCrop(uri)
                }
        } else {
            glideCenterCrop(saveUri!!)
            Log.d("LoadUri", "Уже загружено в loadImageCategorySkazka saveUri выполнено = $saveUri")
        }
    }

    @SuppressLint("CheckResult")
    fun loadImageNameSkazka(skazkiCatModel: SkazkiCatModel) {

        var saveUri = skazkiCatModel.Items?.ImageNameSkazkaForLoad

        if (saveUri == null) {
            storage.getReferenceFromUrl(skazkiCatModel.Items?.ImageNameSkazka!!)
                .downloadUrl
                .addOnSuccessListener { uri ->
                    saveUri = uri
                    glideCenterCrop(uri)
                }
        } else {
            glideCenterCrop(saveUri!!)
        }
    }

    @SuppressLint("CheckResult")
    fun loadImageInteractive(interactiveModel: InteractiveModel) {
        var saveUri = interactiveModel.ImageForLoad
        if (saveUri == null) {
            storage.getReferenceFromUrl(interactiveModel.Image)
                .downloadUrl
                .addOnSuccessListener { uri ->
                    saveUri = uri
                    glideCenterCrop(uri)
                }
        } else {
            glideCenterCrop(saveUri!!)
        }
    }

    @SuppressLint("CheckResult")
    fun loadFullImageInteractive(interactiveModel: InteractiveModel) {

        var saveUri = interactiveModel.ImageForLoad
        if (saveUri == null) {
            storage.getReferenceFromUrl(interactiveModel.Image)
                .downloadUrl
                .addOnSuccessListener { uri ->
                    saveUri = uri
                    glide(uri)
                }
        } else {
            glide(saveUri!!)
        }
    }

    private fun glideCenterCrop(uri: Uri) {
        Glide.with(context)
            .load(uri)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }

    private fun glide(uri: Uri) {
        Glide.with(context)
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
}